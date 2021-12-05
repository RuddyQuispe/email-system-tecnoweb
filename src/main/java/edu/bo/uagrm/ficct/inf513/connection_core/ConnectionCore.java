package edu.bo.uagrm.ficct.inf513.connection_core;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 08:24
 */
public class ConnectionCore {

    private static boolean isDouble(String s) {
        boolean isValid = true;
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }

    private static boolean isFloat(String s) {
        boolean isValid = true;
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }

    private static boolean isBoolean(String s) {
        boolean isValid = true;
        try {
            Boolean.parseBoolean(s);
        } catch (NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        POPService popService = new POPService();
//        Thread thread = new Thread(popService);
//        thread.setName("Mail Verification Thread");
//        thread.start();
//        user add [Ronaldo, water Gonzales, 76042142]
//        use_case | action | parameters
//        [ mascota | add | Firulay | 25 | Negro ]
        String command = "product add [200; hola como estas; 20-01-2014; 2099.56; true; false]";
        if (command.indexOf("[") == -1 || command.indexOf("]") == -1) {
            System.out.println("NO ACTION");
            return;
        }
        String useCaseAndAction = command.substring(0, command.indexOf("["));
        useCaseAndAction = useCaseAndAction.toUpperCase();
        System.out.println("ACTION: " + useCaseAndAction);
        String[] listMain = useCaseAndAction.split(" ");
        for (String factor : listMain) {
            System.out.println(factor);
        }
        String emailToSend = "ruddyq18@gmail.com";
        String[] parametersList = command.substring(command.indexOf("[") + 1, command.indexOf("]")).split(";");
        for (String parameter : parametersList) {
            String parameterIndex = parameter.trim();
            System.out.println("-" + parameterIndex + "-");
            System.out.println("isDate: " + isDate(parameterIndex));
            System.out.println("isInteger: " + isInteger(parameterIndex));
            System.out.println("isDouble: " + isDouble(parameterIndex));
            System.out.println("isFloat: " + isFloat(parameterIndex));
            System.out.println("isBoolean: " + isBoolean(parameterIndex));
        }
    }
}
