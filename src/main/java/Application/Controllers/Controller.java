package Application.Controllers;

import Application.Entity.Customer;
import Application.Entity.Product;
import Application.Entity.Purchase;
import Application.Repositories.CustomerRepo;
import Application.Repositories.ProductRepo;
import Application.Repositories.PurchaseRepo;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("model");
    private Map<String, String> currentParameters = null;

    private String currentKey = "";
    private String currentValue = "";

    private CustomerRepo customerRepo = new CustomerRepo(emf);
    private PurchaseRepo purchaseRepo = new PurchaseRepo(emf);
    private ProductRepo productRepo = new ProductRepo(emf);

    public Controller() throws IOException {
        currentParameters = new LinkedHashMap<>();

        // ClassLoader classLoader = App.class.getClassLoader();
        File f = new File(getClass().getClassLoader().getResource("1.json").getFile());

        JsonReader reader = new JsonReader(new FileReader(f));
        handleObject(reader);

        search();
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
        if(!currentValue.equals("") && !currentKey.equals("")){
            System.out.println("Current :" + currentKey + " " + currentValue);
            currentParameters.put(currentKey,currentValue);
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
                currentParameters.put(currentKey,currentValue);
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

    public void search(){
        Iterator<Map.Entry<String, String>> iterator = currentParameters.entrySet().iterator();
        while (iterator.hasNext()){
            List<Customer> customersList = new ArrayList<>();
            Map.Entry<String,String> entry = iterator.next();
            try {
                switch (entry.getKey()) {
                    case ("lastName"):
                        customersList = customerRepo.findBySurname(entry.getValue());
                        System.out.println(customersList);
                        break;
                    case ("productName"):
                        String title = entry.getValue();
                        if(iterator.hasNext()){
                            entry = iterator.next();
                            if(entry.getKey().equals("minTimes")){
                                int minTimes = Integer.parseInt(entry.getValue());
                                Product product = productRepo.findByTitle(title);
                                customersList = purchaseRepo.findCustomerWithProductAndMoreQuantity(product.getId_product(), minTimes);
                            }
                        }
                        System.out.println(customersList);
                        break;
                    case ("minExpenses"):
                        int min = Integer.parseInt(entry.getValue());
                        if(iterator.hasNext()){
                            entry = iterator.next();
                            if(entry.getKey().equals("maxExpenses")){
                                int max = Integer.parseInt(entry.getValue());
                                for(Customer DBCustomers : customerRepo.findAll()){
                                    int expenses = purchaseRepo.getAllExpenses(DBCustomers.getId_customer());
                                    if(expenses > min && expenses < max)
                                        customersList.add(DBCustomers);
                                }
                            }
                        }
                        System.out.println(customersList);
                        break;
                    case ("badCustomers"):
                        HashMap<Customer,Integer> customerQuantity = new HashMap<>();
                        for(Customer customer : customerRepo.findAll()){
                            int quantity = 0;
                            for(Purchase purchase : purchaseRepo.findByIdCustomer(customer.getId_customer())){
                                quantity += purchase.getQuantity();
                            }
                            customerQuantity.put(customer, quantity);
                        }
                        customerQuantity.entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByValue())
                                .forEach(System.out::println);

                        break;
                    case ("startDate"):
                        try {
                            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
                            if(iterator.hasNext()){
                                entry = iterator.next();
                                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getValue());
                                for(Customer customer : customerRepo.findAll()){
                                    System.out.println("start");
                                    System.out.println(purchaseRepo.findPurchasesBetweenDates(start,end));
                                }
                            }
                        } catch (ParseException parseException){
                                parseException.printStackTrace();
                        }
                            break;
                    default:

                }
            }catch (NoResultException e){
                System.out.println("NoResult");
            }
        }
    }
}
