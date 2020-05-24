package Application.Controllers;

import Application.Entity.Customer;
import Application.Entity.Product;
import Application.Entity.Purchase;
import Application.Repositories.CustomerRepo;
import Application.Repositories.ProductRepo;
import Application.Repositories.PurchaseRepo;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Controller {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("model");
    private Map<String, String> currentParameters = null;

    private String currentKey = "";
    private String currentValue = "";

    private CustomerRepo customerRepo = new CustomerRepo(emf);
    private PurchaseRepo purchaseRepo = new PurchaseRepo(emf);
    private ProductRepo productRepo = new ProductRepo(emf);

    private String homeDirectory = "src/outFile/";
    private String fileName = "out.json";


    public Controller(String type, String inputFile, String outputFile) {
        currentParameters = new LinkedHashMap<>();

        try (JsonReader reader = new JsonReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(inputFile), "UTF-8"))) {
            handleObject(reader);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something wrong with input file.");
            exit(0);
        } catch (NullPointerException n){
            n.printStackTrace();
            System.out.println("No such file.");
        }

        if (!outputFile.equals("") && !outputFile.equals(inputFile))
            fileName = outputFile;

        File out = new File(homeDirectory + fileName);
        search(type, out);
        System.out.println("Done");
    }

    private void handleObject(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.BEGIN_ARRAY))
                handleArray(reader);
            else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                return;
            } else
                handleNonArrayToken(reader, token);
        }
        if (!currentValue.equals("") && !currentKey.equals("")) {
            System.out.println("Current :" + currentKey + " " + currentValue);
            currentParameters.put(currentKey, currentValue);
            currentKey = "";
            currentValue = "";
        }
    }

    /**
     * Handle a json array. The first token would be JsonToken.BEGIN_ARRAY.
     * Arrays may contain objects or primitives.
     *
     * @param reader
     * @throws IOException
     */
    public void handleArray(JsonReader reader) throws IOException {
        reader.beginArray();
        while (true) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                /*if(!currentValue.equals("") && !currentKey.equals("")){
                    //   System.out.println("Current :" + currentKey + " " + currentValue);
                    currentKey = "";
                    currentValue = "";
                }*/
                reader.endObject();
            } else
                handleNonArrayToken(reader, token);
        }
    }

    /**
     * Handle non array non object tokens
     *
     * @param reader
     * @param token
     * @throws IOException
     */
    public void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException {
        if (token.equals(JsonToken.NAME)) {
            if (!currentKey.equals("") && !currentValue.equals("")) {
                System.out.println("Current :" + currentKey + " " + currentValue);
                currentParameters.put(currentKey, currentValue);
                currentKey = "";
                currentValue = "";
            }
            currentKey = reader.nextName();
            System.out.println(currentKey);
        } else if (token.equals(JsonToken.STRING)) {
            currentValue = reader.nextString();
            System.out.println(currentValue);
        } else if (token.equals(JsonToken.NUMBER)) {
            currentValue = String.valueOf(reader.nextInt());
            System.out.println(currentValue);
        } else {
            reader.skipValue();
        }
    }

    public void search(String type, File file) {
        try (JsonWriter writer = new JsonWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.beginObject();
            writer.name("type");
            writer.value(type);

            writer.name("results");
            writer.beginArray();

            Iterator<Map.Entry<String, String>> iterator = currentParameters.entrySet().iterator();
            while (iterator.hasNext()) {
                List<Customer> customersList = new ArrayList<>();
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

                Map.Entry<String, String> entry = iterator.next();

                switch (entry.getKey()) {
                    case ("lastName"):
                        customersList = customerRepo.findBySurname(entry.getValue());
                        System.out.println(customersList);

                        parameters.put("lastName", entry.getValue());

                        printBeginCriteria(writer, parameters);
                        printCustomersList(writer, customersList);
                        printEndCriteria(writer);

                        break;
                    case ("productName"):
                        String title = entry.getValue();
                        if (iterator.hasNext()) {
                            entry = iterator.next();
                            if (entry.getKey().equals("minTimes")) {
                                try {
                                    int minTimes = Integer.parseInt(entry.getValue());
                                    Product product = null;
                                    if (minTimes > 0) {
                                        try {
                                            product = productRepo.findByTitle(title);
                                        } catch (NoResultException e) {
                                            parameters.put("productName", title);
                                            printBeginCriteria(writer, parameters);
                                            printEndCriteria(writer);
                                            break;
                                        }
                                        customersList = purchaseRepo.findCustomerWithProductAndMoreQuantity(product.getId_product(), minTimes);

                                        parameters.put("productName", title);
                                        parameters.put("minTimes", String.valueOf(minTimes));
                                        printBeginCriteria(writer, parameters);
                                        printCustomersList(writer, customersList);
                                        printEndCriteria(writer);
                                    } else {
                                        writer.nullValue();
                                        printError("Wrong argument for minTimes. Expected value greater than 0.");
                                    }
                                } catch (NumberFormatException e) {
                                    writer.nullValue();
                                    printError("Wrong argument for minTimes. Expected: Number, Received: " + entry.getValue());
                                }
                            } else {
                                writer.nullValue();
                                printError("Wrong next argument for productName. Expected: minTimes, Received: " + entry.getKey());
                            }
                        } else {
                            writer.nullValue();
                            printError("Not all arguments for productName.");
                        }
                        System.out.println(customersList);
                        break;
                    case ("minExpenses"):
                        try {
                            int min = Integer.parseInt(entry.getValue());
                            if (min >= 0) {
                                if (iterator.hasNext()) {
                                    entry = iterator.next();
                                    if (entry.getKey().equals("maxExpenses")) {

                                        int max = Integer.parseInt(entry.getValue());
                                        if (max > min) {
                                            for (Customer DBCustomers : customerRepo.findAll()) {
                                                int expenses = purchaseRepo.getAllExpenses(DBCustomers.getId_customer());
                                                if (expenses > min && expenses < max)
                                                    customersList.add(DBCustomers);
                                            }

                                            parameters.put("minExpenses", String.valueOf(min));
                                            parameters.put("maxExpenses", String.valueOf(max));
                                            printBeginCriteria(writer, parameters);
                                            printCustomersList(writer, customersList);
                                            printEndCriteria(writer);
                                        } else {
                                            writer.nullValue();
                                            printError("Bad argument for maxExpenses. Expected value greater than minExpenses.");
                                        }

                                    } else {
                                        writer.nullValue();
                                        printError("Wrong next argument for minExpenses. Expected: maxExpenses, Received: " + entry.getKey());
                                    }
                                } else {
                                    writer.nullValue();
                                    printError("Not all arguments for minExpenses.");
                                }
                            } else {
                                writer.nullValue();
                                printError("Bad argument for minExpenses. Expected value greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            writer.nullValue();
                            printError("Wrong value for " + entry.getKey() + ". Expected: Number, Received: " + entry.getValue());
                        }
                        System.out.println(customersList);
                        break;
                    case ("badCustomers"):
                        try {
                            int quantity = Integer.parseInt(entry.getValue());
                            if (quantity > 0) {
                                HashMap<Customer, Integer> customerQuantity = new HashMap<>();
                                LinkedList<Customer> linkedList = new LinkedList<>();
                                for (Customer customer : customerRepo.findAll()) {
                                    int cur = 0;
                                    for (Purchase purchase : purchaseRepo.findByIdCustomer(customer.getId_customer())) {
                                        cur += purchase.getQuantity();
                                    }
                                    customerQuantity.put(customer, cur);
                                }
                           /* customerQuantity = customerQuantity.entrySet()
                                    .stream()
                                    .sorted(Map.Entry.comparingByValue())
                                    .forEach(System.out::println);*/
                                customerQuantity = customerQuantity.entrySet()
                                        .stream()
                                        .sorted(Map.Entry.comparingByValue())
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

                                Iterator<Map.Entry<Customer, Integer>> iter = customerQuantity.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry<Customer, Integer> e = iter.next();
                                    System.out.println(e.getKey() + "  " + e.getValue());
                                }
                                iter = customerQuantity.entrySet().iterator();
                                for (int i = 0; i < quantity; i++) {
                                    Map.Entry<Customer, Integer> e = null;
                                    if (iter.hasNext())
                                        e = iter.next();
                                    else {
                                        writer.nullValue();
                                        printError("Wrong Quantity for badCustomers.");
                                        break;
                                    }

                                    customersList.add(e.getKey());
                                }


                                parameters.put("badCustomers", String.valueOf(quantity));
                                printBeginCriteria(writer, parameters);
                                printCustomersList(writer, customersList);
                                printEndCriteria(writer);

                            } else {
                                writer.nullValue();
                                printError("Wrong Quantity for badCustomers. Expected value greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            writer.nullValue();
                            printError("Wrong value for badCustomers. Expected: Number, Received: " + entry.getValue());
                        }

                        break;
                    case ("startDate"):
                        try {
                            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
                            if (iterator.hasNext()) {
                                entry = iterator.next();
                                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
                                if (start.getTime() < end.getTime()) {
                                    for (Customer customer : customerRepo.findAll()) {

                                        parameters.put("name", customer.getName());

                                        printBeginCriteria(writer, parameters);
                                        System.out.println("start");
                                        List<Purchase> purchaseList = new ArrayList<>();
                                        purchaseList = purchaseRepo.findPurchasesBetweenDates(start, end);
                                        System.out.println(purchaseList);
                                        printPurchasesList(writer, purchaseList);
                                        printEndCriteria(writer);
                                    }
                                } else {
                                    writer.nullValue();
                                    printError("Wrong start Date. Expected startDate earlier endDate.");
                                }

                            }
                        } catch (ParseException parseException) {
                            writer.nullValue();
                            printError("Wrong value for " + entry.getKey() + ". Expected: Date with format yyyy-MM-dd, Received: " + entry.getValue());
                        }
                        break;
                    default:

                }

            }
            writer.endArray();
            writer.endObject();

        } catch (IOException e) {
            System.out.println("Something wrong with output file.");
            e.printStackTrace();
            exit(0);
        }
    }


    private void printBeginCriteria(JsonWriter writer, LinkedHashMap<String, String> criteria) throws IOException {
        writer.beginObject();
        writer.name("criteria");
        writer.beginObject();
        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            writer.name(entry.getKey());
            writer.value(entry.getValue());
        }
        writer.endObject();
        writer.name("results:");
        writer.beginArray();
    }

    private void printCustomersList(JsonWriter writer, List<Customer> customersList) throws IOException {
        for (Customer customer : customersList) {
            writer.beginObject();
            writer.name("last").value(customer.getName());
            writer.endObject();
        }
    }

    private void printPurchasesList(JsonWriter writer, List<Purchase> purchaseList) throws IOException {
        for (Purchase purchase : purchaseList) {
            writer.beginObject();
            writer.name(purchase.getProduct().getProduct_title()).value(Math.round(purchase.getProduct().getPrice() * 100.0) / 100.0);
            writer.endObject();
        }
    }

    private void printEndCriteria(JsonWriter writer) throws IOException {
        writer.endArray();
        writer.endObject();
    }

    private void printError(String message) {

        File out = new File(homeDirectory + fileName);
        try (JsonWriter writer = new JsonWriter(new FileWriter(out))) {
            writer.beginObject();
            writer.name("type");
            writer.value("error");
            writer.name("message");
            writer.value(message);
            writer.endObject();
            writer.close();
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
