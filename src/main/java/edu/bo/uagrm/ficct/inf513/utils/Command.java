package edu.bo.uagrm.ficct.inf513.utils;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 15/9/21 10:50
 */
public class Command {
    private static final String END = "\r\n";

    public static String user(String user) {
        return "USER " + user + END;
    }

    public static String pass(String pass) {
        return "PASS " + pass + END;
    }

    public static String stat() {
        return "STAT " + END;
    }

    public static String list() {
        return "LIST " + END;
    }

    public static String quit() {
        return "QUIT" + END;
    }

    public static String retr(int email) {
        return "RETR " + email + END;
    }

    public static String dele(int email) {
        return "DELE " + email + END;
    }

    public static boolean err(String responseMessageEmail) {
        return responseMessageEmail.contains("-ERR");
    }
}