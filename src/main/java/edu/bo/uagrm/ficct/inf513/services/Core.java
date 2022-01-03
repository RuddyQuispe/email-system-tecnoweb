package edu.bo.uagrm.ficct.inf513.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.bo.uagrm.ficct.inf513.business.gestion_ingreso_egreso.EgresoBusiness;
import edu.bo.uagrm.ficct.inf513.business.gestion_ingreso_egreso.IngresoBusiness;
import edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte.*;
import edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades.SocioBusiness;
import edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades.EmpleadoBusiness;
import edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades.ActaReunionesBusiness;
import edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades.AsistenciaBusiness;
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
    private String message;


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
                            new ArrayList<String>(Arrays.asList("PAGO", "REGISTRAR, MODIFICAR, LISTAR, ELIMINAR", "Gestionar pagos que realizan los socios", HTMLBuilder.buildButton("LISTAR", "PAGO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("MULTA", "REGISTRAR, MODIFICAR, LISTAR, ELIMINAR, AGREGAR_SOCIO", "Gestionar multa por sancion a los socios", HTMLBuilder.buildButton("LISTAR", "MULTA LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("SOCIO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestionar socio del mercado", HTMLBuilder.buildButton("LISTAR", "SOCIO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("INGRESO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestionar Ingresos", HTMLBuilder.buildButton("LISTAR", "INGRESO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("EGRESO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestionar Egresos", HTMLBuilder.buildButton("LISTAR", "EGRESO LISTAR", "INFO"))),
                            //new ArrayList<String>(Arrays.asList("KARDEX", "LISTAR", "Visualizar el kardex de un socio", HTMLBuilder.buildButton("LISTAR", "KARDEX LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("SOCIO", "REGISTRAR, MODIFICAR,ELIMINAR", "gestionar socio", HTMLBuilder.buildButton("LISTAR", "SOCIO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("EMPLEADO", "REGISTRAR, MODIFICAR,ELIMINAR", "gestionar empleado", HTMLBuilder.buildButton("LISTAR", "EMPLEADO LISTAR", "INFO"))),
                            new ArrayList<String>(Arrays.asList("ACTA_REUNION", "REGISTRAR, MODIFICAR,ELIMINAR", "gestionar acta de reunion", HTMLBuilder.buildButton("LISTAR", "ACTA_REUNION LISTAR", "INFO"))),
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
                AsistenciaBusiness asistenciaBusiness = new AsistenciaBusiness();
                switch (this.action){
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = asistenciaBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat = "";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(1).split("-");
                            dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "\uD83D\uDD8A️",
                                            "ASISTENCIA MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; "+ dateFormat + "; " + rowInput.get(2) + "; " + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "\uD83D\uDDD1️",
                                                    "ASISTENCIA ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            )
                            );
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR ASISTENCIA",
                                "ASISTENCIA REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + " 02-01-2022;  asistencia a reunion;" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA ASISTENCIA </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = asistenciaBusiness.createAsistencia(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ASISTENCIA LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = asistenciaBusiness.updateAsistencia(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ASISTENCIA LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case  TokenAction.ELIMINAR:
                        message = asistenciaBusiness.removeAsistencia(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ASISTENCIA LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ASISTENCIA LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.ACTA_REUNIONES:
                ActaReunionesBusiness actaReunionesBusiness = new ActaReunionesBusiness();
                switch (this.action){
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = actaReunionesBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat = "";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(1).split("-");
                            dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "\uD83D\uDD8A️",
                                            "ACTA_REUNION MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; "+ dateFormat + "; " + rowInput.get(2) + "; " + rowInput.get(3) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "\uD83D\uDDD1️",
                                                    "ACTA_REUNION ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            )
                            );
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR ACTA_REUNION",
                                "ACTA_REUNION REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + " 02-01-2022;  acta de reunion de informacion acerca de las nuevas normas; zuleny" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA ACTA_REUNION </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = actaReunionesBusiness.createActaReunion(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ACTA_REUNION LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = actaReunionesBusiness.updateActaReunion(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ACTA_REUNION LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case  TokenAction.ELIMINAR:
                        message = actaReunionesBusiness.removeActaReunion(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ACTA_REUNION LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "ACTA_REUNION LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.MULTA:
                MultaBusiness multaBusiness = new MultaBusiness();
                switch (this.action) {
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listMulta = multaBusiness.findAll();
                        ArrayList<String> inputHeader = listMulta.remove(0);
                        inputHeader.add("acciones");
                        for (ArrayList<String> rowInput : listMulta) {
                            rowInput.add(
                                    "<div style=\"display: block;\">" +
                                            HTMLBuilder.buildButton(
                                                    "MODIFICAR",
                                                    "MULTA MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + rowInput.get(1) + "; " + rowInput.get(2) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "ELIMINAR",
                                                    "MULTA ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ) +
                                            HTMLBuilder.buildButton(
                                                    "LISTAR SOCIOS",
                                                    "MULTA LISTAR_SOCIO_MULTA [" + rowInput.get(0) + "]",
                                                    "INFO"
                                            ) +
                                            "</div>");
                        }
                        String buttonCreate = "<br><br><br>" + HTMLBuilder.buildButton(
                                "REGISTRAR MULTA",
                                "MULTA REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "descripcion(STRING); monto(DOUBLE)" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        return HTMLBuilder.generateTable("LISTA MULTA" + buttonCreate, inputHeader, listMulta);
                    case TokenAction.REGISTRAR:
                        this.message = multaBusiness.createMulta(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(this.message) : HTMLBuilder.buildMessageSuccess(this.message);
                    case TokenAction.MODIFICAR:
                        this.message = multaBusiness.updateMulta(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(this.message) : HTMLBuilder.buildMessageSuccess(this.message);
                    case TokenAction.ELIMINAR:
                        this.message = multaBusiness.removeMulta(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                    case TokenAction.LISTAR_SOCIO_MULTA:
                        ArrayList<ArrayList<String>> listSocioMulta = multaBusiness.getListByIdMulta(this.parameters);
                        ArrayList<String> inputHeaderSocioMulta = listSocioMulta.remove(0);
                        inputHeaderSocioMulta.add("acciones");
                        for (ArrayList<String> rowInput : listSocioMulta) {
                            rowInput.add(
                                    "<div style=\"display: block;\">" +
                                            HTMLBuilder.buildButton(
                                                    "ELIMINAR",
                                                    "MULTA ELIMINAR_SOCIO_MULTA " + Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0) + "; " + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ) +
                                            "</div>");
                        }
                        String buttonCreteSocioMulta = "<br><br><br>" + HTMLBuilder.buildButton(
                                "REGISTRAR SOCIO EN MULTA",
                                "MULTA ADICIONAR_SOCIO_MULTA " + Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0) +"; "+ "nombre(STRING)" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        return HTMLBuilder.generateTable("LISTA SOCIOS DE MULTA: " + this.parameters.get(0).trim() + buttonCreteSocioMulta, inputHeaderSocioMulta, listSocioMulta);
                    case TokenAction.ADICIONAR_SOCIO_MULTA:
                        this.message = multaBusiness.createSocioMulta(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR_SOCIO_MULTA [" + this.parameters.get(0).trim() + "]",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                    case TokenAction.ELIMINAR_SOCIO_MULTA:
                        this.message = multaBusiness.removeSocioMulta(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR_SOCIO_MULTA [" + this.parameters.get(0).trim() + "]",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                    default:
                        this.message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "<br>";
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "MULTA LISTAR",
                                "INFO"
                        );
                        return this.message;
                }
            case TokenUseCase.INGRESO:
                IngresoBusiness ingresoBusiness = new IngresoBusiness();
                switch (this.action){
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = ingresoBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat = "";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(2).split("-");
                            dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "\uD83D\uDD8A️",
                                            "INGRESO MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + rowInput.get(1)+"; "+ dateFormat + "; " + rowInput.get(3) + "; " + rowInput.get(4) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "\uD83D\uDDD1️",
                                                    "INGRESO ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            )
                            );
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR INGRESO",
                                "INGRESO REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "ingreso por aportes voluntarios; 29-12-2021; 120; zuleny" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA INGRESOS </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = ingresoBusiness.createIngreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "INGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = ingresoBusiness.updateIngreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "INGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case  TokenAction.ELIMINAR:
                        message = ingresoBusiness.removeIngreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "INGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "INGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.EGRESO:
                EgresoBusiness egresoBusiness = new EgresoBusiness();
                switch (this.action){
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = egresoBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat = "";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(3).split("-");
                            dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "\uD83D\uDD8A️",
                                            "EGRESO MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + rowInput.get(1)+ "; "+rowInput.get(2)+ "; "+dateFormat + "; " + rowInput.get(4) + "; " + rowInput.get(5) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "\uD83D\uDDD1️",
                                                    "EGRESO ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            )
                            );
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR EGRESO",
                                "EGRESO REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "pago de servico basico de agua; 1200; 29-12-2021; Empresa COTAS; zuleny" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("LISTA EGRESOS </br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.REGISTRAR:
                        String message = egresoBusiness.createEgreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "EGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.MODIFICAR:
                        message = egresoBusiness.updateEgreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "EGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case  TokenAction.ELIMINAR:
                        message = egresoBusiness.removeEgreso(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "EGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    default:
                        message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "EGRESO LISTAR",
                                "INFO"
                        );
                        htmlResponse = message;
                        break;
                }
                break;
            case TokenUseCase.APORTE:
                AporteBusiness inputBusiness = new AporteBusiness();
                switch (this.action) {
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = inputBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.set(5, inputHeader.get(5) + "(%)");
                        inputHeader.add("acciones");
                        for (ArrayList<String> rowInput : listInput) {
                            rowInput.add(
                                    "<div style=\"display: block;\">" +
                                            HTMLBuilder.buildButton(
                                                    "MODIFICAR",
                                                    "APORTE MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + rowInput.get(1) + "; " + rowInput.get(2) + "; " + rowInput.get(3) + "; " + rowInput.get(4) + "; " + rowInput.get(5) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "ELIMINAR",
                                                    "APORTE ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ) +
                                            "</div>");
                        }
                        String buttonCreate = "<br><br><br>" + HTMLBuilder.buildButton(
                                "REGISTRAR APORTE",
                                "APORTE REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "descripcion(STRING); fecha_inicio(DD-MM-YYYY); fecha_limit(DD-MM-YYYY); monto(DOUBLE); mora(INT)" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        return HTMLBuilder.generateTable("LISTA APORTES  " + buttonCreate, inputHeader, listInput);
                    case TokenAction.REGISTRAR:
                        this.message = inputBusiness.createAporte(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(this.message) : HTMLBuilder.buildMessageSuccess(this.message);
                    case TokenAction.MODIFICAR:
                        this.message = inputBusiness.updateAporte(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(this.message) : HTMLBuilder.buildMessageSuccess(this.message);
                    case TokenAction.ELIMINAR:
                        this.message = inputBusiness.removeAporte(this.parameters);
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        return this.message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(this.message) : HTMLBuilder.buildMessageSuccess(this.message);
                    default:
                        this.message = "COMANDO " + this.action + " NO HAY ACCION PARA EL CASO DE USO: " + this.useCase + "</br>";
                        this.message = this.message + "<br><br><br>" + HTMLBuilder.buildButton(
                                "LISTAR",
                                "APORTE LISTAR",
                                "INFO"
                        );
                        return this.message;
                }
            case TokenUseCase.PAGO:
                PagoBusiness pagoBusiness = new PagoBusiness();
                switch (this.action) {
                    case TokenAction.LISTAR:
                        ArrayList<ArrayList<String>> listInput = pagoBusiness.findAll();
                        ArrayList<String> inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        String[] dateArr;
                        String dateFormat = "";
                        for (ArrayList<String> rowInput : listInput) {
                            dateArr = rowInput.get(1).split("-");
                            dateFormat = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "\uD83D\uDD8A️",
                                            "PAGO MODIFICAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; " + dateFormat + "; " + rowInput.get(3) + "; " + rowInput.get(4) + "; " + rowInput.get(5) + Token.TOKEN_PARAMETERS_CLOSE,
                                            "WARNING") +
                                            HTMLBuilder.buildButton(
                                                    "\uD83D\uDDD1️",
                                                    "PAGO ELIMINAR " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            ) +
                                            HTMLBuilder.buildButton("Aportes", "PAGO LISTAR_APORTE_PAGO " + Token.TOKEN_PARAMETERS_OPEN+ rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE, "INFO") +
                                            HTMLBuilder.buildButton("Multas", "PAGO LISTAR_MULTA_PAGO " + Token.TOKEN_PARAMETERS_OPEN+ rowInput.get(0) + Token.TOKEN_PARAMETERS_CLOSE, "INFO")
                            );
                        }
                        String buttonCreate = HTMLBuilder.buildButton(
                                "REGISTRAR PAGO",
                                "PAGO REGISTRAR " + Token.TOKEN_PARAMETERS_OPEN + "25-12-2021; 123434924; stephani; zuleny" + Token.TOKEN_PARAMETERS_CLOSE,
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
                    case TokenAction.LISTAR_MULTA_PAGO:
                        listInput = pagoBusiness.findAllMultaPago(this.parameters);
                        inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        for (ArrayList<String> rowInput : listInput) {
                            rowInput.add(
                                            HTMLBuilder.buildButton(
                                                    "Eliminar️",
                                                    "PAGO ELIMINAR_MULTA_PAGO " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) + "; "+rowInput.get(1)+ Token.TOKEN_PARAMETERS_CLOSE,
                                                    "DANGER"
                                            )
                            );
                        }
                        buttonCreate = HTMLBuilder.buildButton(
                                "ADICIONAR MULTA ",
                                "PAGO ADICIONAR_MULTA_PAGO " + Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim() + "; 1" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("MULTAS PAGADAS DEL PAGO N° " + this.parameters.get(0).trim() + "</br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.ADICIONAR_MULTA_PAGO:
                        message = pagoBusiness.createMultaPago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR PAGO DE MULTAS",
                                "PAGO LISTAR_MULTA_PAGO "+ Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim()+ Token.TOKEN_PARAMETERS_CLOSE,
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.ELIMINAR_MULTA_PAGO:
                        message = pagoBusiness.removeMultaPago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR PAGO DE MULTAS",
                                "PAGO LISTAR_MULTA_PAGO "+ Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim()+ Token.TOKEN_PARAMETERS_CLOSE,
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.LISTAR_APORTE_PAGO:
                        listInput = pagoBusiness.findAllAportePago(this.parameters);
                        inputHeader = listInput.remove(0);
                        inputHeader.add("acciones");
                        for (ArrayList<String> rowInput : listInput) {
                            rowInput.add(
                                    HTMLBuilder.buildButton(
                                            "Eliminar️",
                                            "PAGO ELIMINAR_APORTE_PAGO " + Token.TOKEN_PARAMETERS_OPEN + rowInput.get(0) +  "; " +rowInput.get(1)+ Token.TOKEN_PARAMETERS_CLOSE,
                                            "DANGER"
                                    )
                            );
                        }
                        buttonCreate = HTMLBuilder.buildButton(
                                "ADICIONAR APORTE",
                                "PAGO ADICIONAR_APORTE_PAGO " + Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim() +"; 1" + Token.TOKEN_PARAMETERS_CLOSE,
                                "PRIMARY"
                        );
                        htmlResponse = HTMLBuilder.generateTable("APORTES PAGADOS DEL PAGO N° " + this.parameters.get(0).trim() + "</br>" + buttonCreate, inputHeader, listInput);
                        break;
                    case TokenAction.ADICIONAR_APORTE_PAGO:
                        message = pagoBusiness.createAportePago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR PAGO DE APORTES",
                                "PAGO LISTAR_APORTE_PAGO " + Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim()+ Token.TOKEN_PARAMETERS_CLOSE,
                                "INFO"
                        );
                        htmlResponse = message.contains("ERROR: ") ?
                                HTMLBuilder.buildMessageError(message) : HTMLBuilder.buildMessageSuccess(message);
                        break;
                    case TokenAction.ELIMINAR_APORTE_PAGO:
                        message = pagoBusiness.removeAportePago(this.parameters);
                        message = message + "</br>" + HTMLBuilder.buildButton(
                                "LISTAR PAGO DE APORTES",
                                "PAGO LISTAR_APORTE_PAGO "+ Token.TOKEN_PARAMETERS_OPEN + this.parameters.get(0).trim()+ Token.TOKEN_PARAMETERS_CLOSE,
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
