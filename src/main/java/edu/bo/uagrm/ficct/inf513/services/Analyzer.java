package edu.bo.uagrm.ficct.inf513.services;

import edu.bo.uagrm.ficct.inf513.utils.Token;
import edu.bo.uagrm.ficct.inf513.utils.TokenAction;
import edu.bo.uagrm.ficct.inf513.utils.TokenUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Date;

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
            // verify doesn't exists brackets "[" "]"
            if (command.indexOf(Token.TOKEN_PARAMETERS_OPEN) == -1 || command.indexOf(Token.TOKEN_PARAMETERS_CLOSE) == -1) {
                System.out.println("No tiene parametros: " + command + " - " + command.equals(Token.HELP));
                if (command.equalsIgnoreCase(Token.HELP)) {
                    System.out.println("es HELP");
                    this.action = command;
                    this.error = false;
                } else {
                    System.out.println("no es help");
                    String[] listHead = command.toUpperCase().split(" ");
                    if ((listHead.length == 2 && listHead[1].trim().equalsIgnoreCase(TokenAction.LISTAR)) ||
                            (listHead.length == 2 && listHead[0].trim().equalsIgnoreCase(TokenUseCase.REPORTE_ESTADISTICA))) {
                        System.out.println("son 2 attr y es listar o reporte");
                        this.useCase = listHead[0].trim();
                        this.action = listHead[1].trim();
                        this.error = false;
                    } else {
                        System.out.println("NO EXISTS ACTION " + command);
                        this.error = true;
                    }
                }
            } else {
                // get use case and action
                String useCaseAndAction = command.substring(0, command.indexOf(Token.TOKEN_PARAMETERS_OPEN)).toUpperCase();
                // separate use case and action
                String[] listHead = useCaseAndAction.split(" ");
                // assign use case
                this.useCase = listHead[0];
                // assign action
                this.action = listHead[1];
                // separate each parameters
                String[] parametersList =
                        command.substring(command.indexOf(Token.TOKEN_PARAMETERS_OPEN) + 1,
                                command.indexOf(Token.TOKEN_PARAMETERS_CLOSE)).split(Token.TOKEN_SEPARATOR);
                // add
                for (String parameter : parametersList) {
                    this.parameters.add(parameter.trim());
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
        try {
            Date.valueOf(inDate.trim());
            return true;
        } catch (Exception exception) {
            return false;
        }
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

    public boolean hasError() {
        return error;
    }

    @Override
    public String toString() {
        if (this.error) {
            return "I have an error";
        } else {
            return "Analyzer {" +
                    "useCase='" + this.useCase + '\'' +
                    ", action='" + this.action + '\'' +
                    ", parameters=" + this.parameters.toString() + "\n" +
                    ", error=" + error +
                    '}';
        }
    }

//    public static void main(String[] args) {
//        List<String> commandList = Arrays.asList("user remove [123] ", "Mensaje de prueba desde ficct.uagrm.edu.bo \n",
//                "Mensaje de prueba desde outlook \n",
//                "Mensaje de prueba desde gmail \n",
//                "HELP");
//        for (String command : commandList) {
//            Analyzer analyzer = new Analyzer(command);
//            System.out.println(analyzer.toString());
//        }
//    }
}
