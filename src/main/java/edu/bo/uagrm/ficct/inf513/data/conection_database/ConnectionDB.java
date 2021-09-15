package edu.bo.uagrm.ficct.inf513.data.conection_database;

import edu.bo.uagrm.ficct.inf513.utils.Address;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 4/9/21 21:14
 */
public class ConnectionDB {
    private String connectionURL;
    private Connection connectionDB;
    private static ConnectionDB session;

    /**
     * @constructor Initialize the instance connection to database
     */
    private ConnectionDB() {
        // get info to .env file
        Dotenv dotenv = Dotenv.configure()
                // address file .env
                .directory(Address.addressFileENV)
                .load();
        // url JDBC drive connection
        this.connectionURL = "jdbc:postgresql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") +
                "/" + dotenv.get("DB_DATABASE");
        // establishing the connection to the database
        try {
            this.connectionDB = DriverManager.getConnection(this.connectionURL,
                    dotenv.get("DB_USERNAME"),
                    dotenv.get("DB_PASSWD"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.print("Error in establishing session PSQL");
        }
    }

    /**
     * return instance connectionDB
     * Singleton patron
     *
     * @return ConnectionDB instance
     */
    public static ConnectionDB getInstance() {
        if (session == null) {
            session = new ConnectionDB();
        }
        return session;
    }

    public Connection getConnection() {
        return this.connectionDB;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    /**
     * verify is connected to server database
     *
     * @return true if connected, else return false
     */
    public boolean isConnected() {
        try {
            return !this.connectionDB.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Disconnect to database server
     */
    public void closeConnection() {
        try {
            this.connectionDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
