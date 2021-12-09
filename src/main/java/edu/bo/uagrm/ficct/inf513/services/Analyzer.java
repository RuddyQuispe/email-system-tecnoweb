package edu.bo.uagrm.ficct.inf513.services;

import edu.bo.uagrm.ficct.inf513.utils.TokenAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-08 17:45
 */
public class Analyzer {
    private String useCase;
    private String action;
    private List<String> parameters;
    private boolean error;

    public Analyzer(String command) {
        try {
            this.parameters = new ArrayList<>();
//          String command = "product add [200; hola como estas; 20-01-2014; 2099.56; true; false]";
            // verify brackets "[" "]"
            if (command.indexOf("[") == -1 || command.indexOf("]") == -1) {
                String[] listHead = command.toUpperCase().split(" ");
                if (listHead[1] == TokenAction.LISTAR) {
                    this.useCase = listHead[0];
                    this.action = listHead[1];
                    this.error = false;
                } else {
                    System.out.println("NO ACTION");
                    this.error = true;
                }
            } else {
                // get use case and action
                String useCaseAndAction = command.substring(0, command.indexOf("[")).toUpperCase();
                // separate use case and action
                String[] listHead = useCaseAndAction.split(" ");
                // assign use case
                this.useCase = listHead[0];
                // assign action
                this.action = listHead[1];
                // separate each parameters
                String[] parametersList = command.substring(command.indexOf("[") + 1, command.indexOf("]")).split(";");
                // add
                for (String parameter : parametersList) {
                    this.parameters.add(parameter.trim());
//                String parameterIndex = parameter.trim();
//                System.out.println("-" + parameterIndex + "-");
//                System.out.println("isDate: " + isDate(parameterIndex));
//                System.out.println("isInteger: " + isInteger(parameterIndex));
//                System.out.println("isDouble: " + isDouble(parameterIndex));
//                System.out.println("isFloat: " + isFloat(parameterIndex));
//                System.out.println("isBoolean: " + isBoolean(parameterIndex));
                }
                this.error = false;
            }
        } catch (Exception exception) {
            System.out.println("ERROR IN ANALYZER CONSTRUCTOR: " + exception);
            this.error = true;
        }
    }

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

    public String getUseCase() {
        return useCase;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return !this.error ? "Analyzer {" +
                "useCase='" + useCase + '\'' +
                ", action='" + action + '\'' +
                ", parameters=" + parameters.toString() +
                ", error=" + error +
                '}' : "I have an error!!!";
    }
}
