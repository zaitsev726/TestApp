package Application;

import Application.Controllers.Controller;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        String type = "", inputFile = "", outputFile = "";
        if (args.length >= 2) {
            type = args[0];
            inputFile = args[1];
            if (args.length == 3) {
                outputFile = args[2];
            } else {
                System.out.println("To many parameters.");
                exit(0);
            }
        } else {
            System.out.println("Not enough parameters.");
            exit(0);
        }

        if (!type.equals("") && !inputFile.equals("")) {
            Controller controller = new Controller(type, inputFile, outputFile);
        } else {
            System.out.println("Not enough parameters.");
            exit(0);
        }
    }
}
