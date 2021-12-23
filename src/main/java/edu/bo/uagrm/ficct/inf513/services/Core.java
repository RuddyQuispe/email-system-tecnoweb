package edu.bo.uagrm.ficct.inf513.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte.*;
import edu.bo.uagrm.ficct.inf513.utils.HTMLBuilder;
import edu.bo.uagrm.ficct.inf513.utils.Token;
import edu.bo.uagrm.ficct.inf513.utils.TokenAction;
import edu.bo.uagrm.ficct.inf513.utils.TokenUseCase;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-10 22:49
 */
public class Core {
    private String useCase;
    private String action;
    private List<String> parameters;

    private String title;
    private String[] dataHeader;
    private List<String[]> rowsInfoList;


    public Core(String useCase, String action, List<String> parameters) {
        this.useCase = useCase;
        this.action = action;
        this.parameters = parameters;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getUseCase() {
        return this.useCase;
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

    /**
     * process token command and identified action and use case,
     *
     * @return
     */
    public String processApplication() {
        String htmlResponse = "";
        if (this.action.equalsIgnoreCase(Token.HELP)) {
            ArrayList<ArrayList<String>> listHelpManual = new ArrayList<ArrayList<String>>(
                    Arrays.asList(
                            new ArrayList<String>(Arrays.asList("APORTE", "REGISTRAR, MODIFICAR, LISTAR, ELIMINAR", "Gestionar aporte que los socios pagarán", HTMLBuilder.buildButton("LISTAR", "APORTE LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("SOCIO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestionar socio del mercado", HTMLBuilder.buildButton("LISTAR", "SOCIO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("KARDEX", "LISTAR", "Visualizar el kardex de un socio", HTMLBuilder.buildButton("LISTAR", "KARDEX LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("ASISTENCIA", "REGISTRAR, MODIFICAR,ELIMINAR", "gestionar la asistencia de un dia", HTMLBuilder.buildButton("LISTAR", "ASISTENCIA LISTAR", "INFO")))
                    )
            );
            return HTMLBuilder.generateTable(
                    "Manual de Email System Tecnoweb \"Asociacion 4 de octubre\"",
                    new ArrayList<String>(Arrays.asList("Token", "Acciones", "Descripcion", "")),
                    listHelpManual
            );
        }
        switch (this.useCase) {
            case TokenUseCase.USUARIO_EMPLEADO:

                break;
            case TokenUseCase.USUARIO_SOCIO:

                break;
            case TokenUseCase.KARDEX:

                break;
            case TokenUseCase.ASISTENCIA:

                break;
            case TokenUseCase.ACTA_REUNIONES:

                break;
            case TokenUseCase.INGRESO:

                break;
            case TokenUseCase.EGRESO:

                break;
            case TokenUseCase.APORTE:
                AporteBusiness inputBusiness = new AporteBusiness();
                switch (this.action) {
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = inputBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        for (ArrayList<String> rowInput : listInput) {
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "MODIFICAR",
                                            "APORTE MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + rowInput.get(1) + "; " + rowInput.get(2) + "; " + rowInput.get(3) + "; " + rowInput.get(4) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "ELIMINAR",
                                                    "APORTE ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ));
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR APORTE",
                                "APORTE REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "STRING; DD-MM-YYYY; DD-MM-YYYY; DOUBLE" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA APORTES </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = inputBusiness.createAporte(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = inputBusiness.updateAporte(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.ELIMINAR:
                        message = inputBusiness.removeAporte(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.PAGO:
                PagoBusiness pagoBusiness = new PagoBusiness();
                switch (this.action) {
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = pagoBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat ="";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(1).split("-");
                            dateFormat = dateArr[2]+"-"+ dateArr[1]+"-"+dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "MODIFICAR",
                                            "PAGO MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + dateFormat + "; " + rowInput.get(2) + "; " + rowInput.get(3) + "; " + rowInput.get(4) + "; " + rowInput.get(5) + "; " + rowInput.get(6) + "; " + rowInput.get(7) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "ELIMINAR",
                                                    "PAGO ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ));
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR PAGO",
                                "PAGO REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "DD-MM-YYYY; DOUBLE; STRING; DOUBLE; INT; INT; INT" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA PAGOS </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = pagoBusiness.createPago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "PAGO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = pagoBusiness.updatePago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "PAGO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.ELIMINAR:
                        message = pagoBusiness.removePago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "PAGO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "PAGO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.MORA:

                break;
            case TokenUseCase.REPORTE_ESTADISTICA:
                return HTMLBuilder.generateGraphics(
                        "HELLO CHART TECHNOLOGY-WEB",
                        new ArrayList<String>(Arrays.asList("City Ostego", "Oswego country", "New York State", "United States")),
                        new ArrayList<Double>(Arrays.asList(29.0, 19.0, 16.0, 16.0)),
                        new ArrayList<String>(Arrays.asList("red", "blue", "green", "orange"))
                );
            default:
                // send message ("use case unidentified");
                htmlResponse = HTMLBuilder.buildMessageError(
                        "Error de comando en el subject, no se reconoció el comando. </br> " +
                                "si es primera vez envienos un \"HELP\"");
                break;
        }
        return htmlResponse;
    }
}
