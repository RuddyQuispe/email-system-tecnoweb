package edu.bo.uagrm.ficct.inf513.utils;

import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-12 01:02
 */
public class HTMLBuilder {
    private static String BODY_OPEN = "<body style=\"" + CSS.UI_GRADIENT + "\">";
    private static String BODY_CLOSE = "</body>";

    public static String generateTable(String title, String[] headers, List<String[]> data) {
        String htmlContent = BODY_OPEN + getTitleStyle(title) + "</br>";
        String headerTable = "<table style=\" border-collapse: collapse; width: 100%; overflow-x: auto; text-align: left;\">" +
                "<thead><tr>";
        for (String head : headers) {
            headerTable = headerTable +
                    "<th>" + head + "</th>";
        }
        headerTable = headerTable + "</tr></thead>";
        String bodyTable = "<tbody>";
        for (int index = 0; index < data.size(); index++) {
            bodyTable = bodyTable + ((index % 2 == 0) ? "<tr style=\"background-color: #FFFFFF80;\">" : "<tr>");
            for (String info : data.get(index)) {
                bodyTable = bodyTable +
                        "<td>" + info + "</td>";
            }
            bodyTable = bodyTable + "</tr>";
        }
        bodyTable = bodyTable + "</tbody>";
        return htmlContent +
                headerTable +
                bodyTable +
                "</table>" +
                BODY_CLOSE;

    }

    public static String getTitleStyle(String title) {
        return "<h1 style=\"font-family: 'Raleway',sans-serif; font-weight: 800; text-align: center; text-transform: uppercase;\">" + title + "</h1>";
    }

    public static String buildMessageSuccess(String meesage) {
        return meesage;
    }

    public static String buildMessageError(String meesage) {
        return meesage;
    }

//    public static void main(String[] args) {
//        String[] data = {"#", "First", "Last", "Handle"};
//        List<String[]> listData = Arrays.asList(
//                new String[]{"1", "Otto", "Otto", "@mdo"},
//                new String[]{"2", "Jacob", "Jacob", "@fat"},
//                new String[]{"3", "Carol", "Matheus", "@twiter"}
//        );
//        String html = generateTable("Hola Cabezones", data, listData);
//        System.out.println(html);
//    }
}
