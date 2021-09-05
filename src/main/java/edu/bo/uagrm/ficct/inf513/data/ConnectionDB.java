package edu.bo.uagrm.ficct.inf513.data;

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
                .directory("/home/ruddy/IdeaProjects/email-system-tecnoweb/src/main/resources/.env")
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

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public boolean isConnected(){
        try {
            return ! this.connectionDB.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * execute query SQL to the database connected
     * throw a problem set time out or error in query sql
     *
     * @param sqlQuery: any sql query
     * @return object that contains the data produced by the given query; never null
     */
    public ResultSet executeQuerySQL(String sqlQuery) {
        try {
            Statement statement = this.connectionDB.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException throwables) {
            System.out.println("ERROR IN EXECUTE QUERY: " + sqlQuery);
            throwables.printStackTrace();
            return null;
        }
    }
}
