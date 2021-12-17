package edu.bo.uagrm.ficct.inf513.connection_core;

import edu.bo.uagrm.ficct.inf513.services.POPService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-11-20 08:24
 */
public class ConnectionCore {

    public static void main(String[] args) {
        POPService popService = new POPService();
        Thread thread = new Thread(popService);
        thread.setName("Mail System Associate Merchants Thread");
        System.out.println("Initialize: " + thread.getName());
        thread.start();
    }
}
