package Application.Controllers;

import Application.Entity.Customer;
import Application.Entity.Product;
import Application.Repositories.CustomerRepo;
import Application.Repositories.ProductRepo;
import Application.Repositories.PurchaseRepo;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.*;

public class Controller {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("model");
    private HashMap<String, String> currentParameters = null;

    private String currentKey = "";
    private String currentValue = "";

    private CustomerRepo customerRepo = new CustomerRepo(emf);
    private PurchaseRepo purchaseRepo = new PurchaseRepo(emf);
    private ProductRepo productRepo = new ProductRepo(emf);

    public Controller() throws IOException {

      //  ClassLoader classLoader = App.class.getClassLoader();
       // File f = new File(getClass().getClassLoader().getResource("1.json").getFile());

        //JsonReader reader = new JsonReader(new FileReader(f));
      //  handleObject(reader);
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
        currentParameters = new HashMap<>();
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
            currentValue = String.valueOf(reader.nextDouble());
            System.out.println(currentValue);
        } else {
            reader.skipValue();
        }
    }

    public void search(){
        List<Customer> customersList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = currentParameters.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            try {
                switch (entry.getKey()) {
                    case ("lastName"):
                        customersList = customerRepo.findBySurname(entry.getKey());
                        break;
                    case ("productName"):
                        String title = entry.getValue();
                        if(iterator.hasNext()){
                            entry = iterator.next();
                            if(entry.getKey().equals("minTimes")){
                                long minTimes = Long.parseLong(entry.getValue());
                                Product product = productRepo.findByTitle(title);
                                customersList = purchaseRepo.findCustomerWithProductAndMoreQuantity(product.getId_product(), minTimes);
                            }
                        }
                        break;
                    case ("minExpense"):
                        long min = Long.parseLong(entry.getValue());
                        if(iterator.hasNext()){
                            entry = iterator.next();
                            if(entry.getKey().equals("minTimes")){
                                long max = Long.parseLong(entry.getValue());
                                for(Customer DBCustomers : customerRepo.findAll()){
                                    int expenses = purchaseRepo.getAllExpenses(DBCustomers.getId_customer());
                                    if(expenses > min && expenses < max)
                                        customersList.add(DBCustomers);
                                }
                            }
                        }
                        break;
                    case ("badCustomers"):
                        break;

                    default:

                }
            }catch (NoResultException e){
                System.out.println("NoResult");
            }
        }
    }
}
