package edu.bo.uagrm.ficct.inf513.services;

import java.util.Arrays;
import java.util.List;

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
        if (this.action.equals(Token.HELP)) {
            return HTMLBuilder.generateTable(
                    "Manual de Email System Tecnoweb \"Asociacion 4 de octubre\"",
                    new String[]{"Token", "Acciones", "Descripcion", ""},
                    Arrays.asList(
                            new String[]{"EMPLEADO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestiona usuario empleado (secretaria)", HTMLBuilder.buildButton("LISTAR", "EMPLEADO LISTAR", "grupo14sc@tecnoweb.org.bo", "INFO")},
                            new String[]{"SOCIO", "REGISTRAR,MODIFICAR,LISTAR;ELIMINAR", "Gestionar socio del mercado", HTMLBuilder.buildButton("LISTAR", "SOCIO LISTAR", "grupo14sc@tecnoweb.org.bo", "INFO")},
                            new String[]{"KARDEX", "LISTAR", "Visualizar el kardex de un socio", HTMLBuilder.buildButton("LISTAR", "KARDEX LISTAR", "grupo14sc@tecnoweb.org.bo", "INFO")},
                            new String[]{"ASISTENCIA", "REGISTRAR, MODIFICAR,ELIMINAR", "gestionar la asistencia de un dia", HTMLBuilder.buildButton("LISTAR", "ASISTENCIA LISTAR", "grupo14sc@tecnoweb.org.bo", "INFO")}
                    )
            );
            // send action
        } else if (this.useCase.equals(TokenUseCase.USUARIO_EMPLEADO)) {
            if (this.action == TokenAction.LISTAR) {
                String[] data = {"#", "First", "Last", "Handle", "Actions"};
                List<String[]> listData = Arrays.asList(
                        new String[]{"1", "Otto", "Otto", "@mdo", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: yellow; border-radius: 8px;\">LISTAR</a>"},
                        new String[]{"2", "Jacob", "Jacob", "@fat", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: blue; border-radius: 8px;\">LISTAR</a>"},
                        new String[]{"3", "Carol", "Matheus", "@twiter", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: green; border-radius: 8px;\">LISTAR</a>"},
                        new String[]{"4", "Marie", "Carla", "@facebook", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: yellow; border-radius: 8px;\">LISTAR</a>"},
                        new String[]{"5", "Curie", "Dario", "@instagram", "<a href=\"mailto:ruddy_quispe@tecnologia-web.me?Subject=LISTAR APORTES\" style=\"background-color: black; border-radius: 8px;\">LISTAR</a>"}
                );
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

        } else if (this.useCase == TokenUseCase.APORTE) {

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
