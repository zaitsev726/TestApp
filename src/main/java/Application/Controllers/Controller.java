package Application.Controllers;

import Application.App;
import Application.Entity.Customer;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private HashMap<String, String> currentParametrs = null;

    private String currentKey = "";
    private String currentValue = "";

    public Controller() throws IOException {

        System.out.println("1231232131311231323");
        ClassLoader classLoader = App.class.getClassLoader();
        File f = new File(getClass().getClassLoader().getResource("1.json").getFile());

        JsonReader reader = new JsonReader(new FileReader(f));
        System.out.println(f.toString());
        handleObject(reader);
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
            currentParametrs.put(currentKey,currentValue);
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
        currentParametrs = new HashMap<>();
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
                currentParametrs.put(currentKey,currentValue);
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
        List<Customer> customerList = new ArrayList<>();
    }
}
