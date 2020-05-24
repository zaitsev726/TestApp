package Application;

import Application.Controllers.Controller;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args){
        String type = "", inputFile = "" , outputFile = "";
        try {
            type = args[0];
            inputFile = args[1];
            outputFile = args[2];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Not enough parameters.");
            exit(0);
        }
        if(!type.equals("") && !inputFile.equals("")) {
            Controller controller = new Controller(type, inputFile, outputFile);
        }else{
            System.out.println("Not enough parameters.");
            exit(0);
        }
    }
}
