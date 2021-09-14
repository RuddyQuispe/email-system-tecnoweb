import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 4/9/21 21:09
 */

public class Main {
    public static void main(String[] args ){
        System.out.println("Hello World");
        ConnectionDB conn = ConnectionDB.getInstance();
        if (conn.isConnected()){
            System.out.println("CONNECTED");
        }else {
            System.out.println("DISCONNECTED");
        }
    }
}
