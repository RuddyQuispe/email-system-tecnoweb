package edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-25 17:33
 */
public class MultaData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public MultaData() {
        this.connection = ConnectionDB.getInstance();
    }

    public boolean create(String descripcion, double monto) {
        try {
            // string query structure
            String query = "insert into multa(descripcion, monto) values (?,?)";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setDouble(2, monto);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class MultaData > create()");
                throw new SQLException();
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get all multa list
     *
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select id, descripcion, monto from multa order by id";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update multa info by id
     *
     * @param id          : multa id
     * @param descripcion : new description
     * @param monto:      new amount
     * @return true if update successfully; else return false
     */
    public boolean update(int id, String descripcion, double monto) {
        try {
            // string query structure
            String query = "update multa set descripcion=?, monto=? where id=?";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setDouble(2, monto);
            preparedStatement.setInt(3, id);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class MultaData > update()");
                throw new SQLException();
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * removes multa by id
     *
     * @param id: identifier multa
     * @return true if removed success, else returns false
     */
    public boolean remove(int id) {
        try {
            String query = "delete from multa where id=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class MultaData > remove()");
                throw new SQLException();
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
