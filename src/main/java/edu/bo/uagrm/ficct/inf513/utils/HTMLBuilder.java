package edu.bo.uagrm.ficct.inf513.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-12 01:02
 */
public class HTMLBuilder {
    private static final String BODY_OPEN = "<body style=\"" + CSS.UI_GRADIENT + "\">";
    private static final String BODY_CLOSE = "</body>";
    private static final Info info = Info.getInstance();

    public static String generateTable(String title, ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        String htmlContent = BODY_OPEN + getTitleStyle(title) + "</br>";
        String headerTable = "<div style=\"padding: 20px;\"><table style=\" border-collapse: collapse; width: 100%; overflow-x: auto; text-align: left;\">" +
                "<thead><tr style=\"border: 1px solid #4E504E; text-align: left;\">";
        for (String head : headers) {
            headerTable = headerTable +
                    "<th style=\"text-align: left; padding: 8px;\">" + head + "</th>";
        }
        headerTable = headerTable + "</tr></thead>";
        String bodyTable = "<tbody>";
        for (int index = 0; index < data.size(); index++) {
            bodyTable = bodyTable + "<tr style=\"border: 1px solid #4E504E; text-align: left;\">";
            for (String info : data.get(index)) {
                bodyTable = bodyTable +
                        "<td style=\"text-align: left; padding: 8px;\">" + info + "</td>";
            }
            bodyTable = bodyTable + "</tr>";
        }
        bodyTable = bodyTable + "</tbody>";
        return htmlContent +
                headerTable +
                bodyTable +
                "</table>" +
                "</div>" +
                BODY_CLOSE;
    }

    public static String getTitleStyle(String title) {
        return "<h1 style=\"font-family: 'Raleway',sans-serif; font-weight: 800; text-align: center; text-transform: uppercase;\">" + title + "</h1>";
    }

    public static String buildMessageSuccess(String message) {
        return "<div style=\"" + CSS.ALERT_STYLE_SUCCESS + "\">" +
                "<strong>SUCCESS!!</strong> " + message +
                "</div>";
    }

    public static String buildMessageError(String message) {
        return "<div style=\"" + CSS.ALERT_STYLE_DANGER + "\">" +
                "<strong>ERROR!!</strong> " + message +
                "</div>";
    }

    public static String buildButton(String labelButton, String subject, String typeButton) {
        switch (typeButton) {
            case "SUCCESS":
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\" " +
                        "style=\"" + CSS.BUTTON_STYLE_SUCCESS + "\">" +
                        labelButton +
                        "</a>";
            case "WARNING":
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\" " +
                        "style=\"" + CSS.BUTTON_STYLE_WARNING + "\">" +
                        labelButton +
                        "</a>";
            case "DANGER":
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\" " +
                        "style=\"" + CSS.BUTTON_STYLE_DANGER + "\">" +
                        labelButton +
                        "</a>";
            case "PRIMARY":
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\" " +
                        "style=\"" + CSS.BUTTON_STYLE_PRIMARY + "\">" +
                        labelButton +
                        "</a>";
            case "INFO":
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\" " +
                        "style=\"" + CSS.BUTTON_STYLE_INFO + "\">" +
                        labelButton +
                        "</a>";
            default:
                return "<a type=\"button\" href=\"mailto:" +
                        info.environmentVariables.get("SMTP_MAIL") + "?Subject=" + subject + "\">" +
                        labelButton +
                        "</a>";
        }
    }

    // color rgb(116, 194, 92)
    private static String buildProgressBar(String label, double percentValue, String color) {
        return "<p style=\"font-size: 20px;\">" + label + "</p>" +
                "<div style=\"width: 100%; border-radius: 15px;\">" +
                "<div style=\"background-color: " + color + "; color: white; padding: 1%; text-align: right; font-size: 20px; border-radius: 25px; width: " + percentValue + "%;\">" +
                +percentValue + "%</div></div>";
    }

    public static String generateGraphics(String title, ArrayList<String> labelsChart, ArrayList<Double> dataChart, ArrayList<String> colors) {
        String htmlContent = BODY_OPEN + getTitleStyle(title) + "</br>";
        String headerTable = "<div style=\"padding: 20px;\">";
        String chartDetail = "";
        for (int index = 0; index < labelsChart.size(); index++) {
            chartDetail = chartDetail + HTMLBuilder.buildProgressBar(labelsChart.get(index), dataChart.get(index), colors.get(index));
        }
        return htmlContent +
                headerTable +
                chartDetail +
                "</div>" +
                BODY_CLOSE;
    }
}
