package edu.bo.uagrm.ficct.inf513.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte.AporteBusiness;
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
        } else if (this.useCase.equalsIgnoreCase(TokenUseCase.USUARIO_EMPLEADO)) {
            if (this.action == TokenAction.LISTAR) {
                //String html = HTMLBuilder.generateTable("Hola Cabezones", data, listData);
            } else if (this.action == TokenAction.REGISTRAR) {

            } else if (this.action == TokenAction.MODIFICAR) {

            } else if (this.action == TokenAction.ELIMINAR) {

            } else {
                // send message ("action to work unidentified");
            }
        } else if (this.useCase == TokenUseCase.USUARIO_SOCIO) {

        } else if (this.useCase == TokenUseCase.KARDEX) {

        } else if (this.useCase == TokenUseCase.ASISTENCIA) {

        } else if (this.useCase == TokenUseCase.ACTA_REUNIONES) {

        } else if (this.useCase == TokenUseCase.INGRESO) {

        } else if (this.useCase == TokenUseCase.EGRESO) {

        } else if (this.useCase.equalsIgnoreCase(TokenUseCase.APORTE)) {
            AporteBusiness inputBusiness = new AporteBusiness();
            if (this.action.equalsIgnoreCase(TokenAction.LISTAR)) {
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
                return HTMLBuilder.generateTable("LISTA APORTES </br>" + buttonCreate, inputHeader, listInput);
            } else if (this.action.equalsIgnoreCase(TokenAction.REGISTRAR)) {
                String message = inputBusiness.createAporte(this.parameters);
                message = message + "</br>" + HTMLBuilder.buildButton(
                        "LISTAR",
                        "APORTE LISTAR",
                        "INFO"
                );
                return message.contains("ERROR: ") ?
                        HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
            } else if (this.action.equalsIgnoreCase(TokenAction.MODIFICAR)) {
                String message = inputBusiness.updateAporte(this.parameters);
                message = message + "</br>" + HTMLBuilder.buildButton(
                        "LISTAR",
                        "APORTE LISTAR",
                        "INFO"
                );
                return message.contains("ERROR: ") ?
                        HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
            } else if (this.action.equalsIgnoreCase(TokenAction.ELIMINAR)) {
                String message = inputBusiness.removeAporte(this.parameters);
                message = message + "</br>" + HTMLBuilder.buildButton(
                        "LISTAR",
                        "APORTE LISTAR",
                        "INFO"
                );
                return message.contains("ERROR: ") ?
                        HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
            } else {
                String message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                message = message + "</br>" + HTMLBuilder.buildButton(
                        "LISTAR",
                        "APORTE LISTAR",
                        "INFO"
                );
                return message;
            }
        } else if (this.useCase == TokenUseCase.MULTA) {

        } else if (this.useCase == TokenUseCase.PAGO) {

        } else if (this.useCase == TokenUseCase.MORA) {

        } else if (this.useCase == TokenUseCase.REPORTE_ESTADISTICA) {

        } else {
            // send message ("use case unidentified");
            return HTMLBuilder.buildMessageError(
                    "Error de comando en el subject, no se reconoció el comando. </br> " +
                            "si es primera vez envienos un \"HELP\"");
        }
        return "";
    }
}
