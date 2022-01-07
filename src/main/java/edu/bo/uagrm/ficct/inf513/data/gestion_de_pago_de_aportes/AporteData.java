package edu.bo.uagrm.ficct.inf513.data.gestion_de_pago_de_aportes;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-17 23:43
 */
public class AporteData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public AporteData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * save new input to database
     *
     * @param descripcion:     description input's
     * @param fechaInicioPago: initial date to collect
     * @param fechaLimite:     finish date to collect
     * @param monto:           amount to collect
     * @return true if saved successfully, else return false
     */
    public boolean create(String descripcion, Date fechaInicioPago, Date fechaLimite, Double monto, int porcentajeMora) {
        try {
            // string query structure
            String query = "insert into aporte(descripcion, fecha_inicio_pago, fecha_limite, monto, porcentaje_mora) " +
                    "values (?,?,?,?,?)";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setDate(2, fechaInicioPago);
            preparedStatement.setDate(3, fechaLimite);
            preparedStatement.setDouble(4, monto);
            preparedStatement.setInt(5, porcentajeMora);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class AporteData > create()");
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
     * get all input data list
     *
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select id, descripcion,to_char(fecha_inicio_pago,'DD-MM-YYYY') as fecha_inicio_pago,to_char(fecha_limite,'DD-MM-YYYY') as fecha_limite,monto, coalesce (porcentaje_mora ,0) as porcentaje_mora from aporte order by id";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update data input
     *
     * @param id:              identifier input
     * @param descripcion:     new description
     * @param fechaInicioPago: new initial date collect
     * @param fechaLimite:     new limit date
     * @param monto:           new amount collect
     * @return true if updated successfully, else return false
     */
    public boolean update(int id, String descripcion, Date fechaInicioPago, Date fechaLimite, Double monto, int porcentajeMora) {
        try {
            // string query structure
            String query = "update aporte " +
                    "set descripcion=?, fecha_inicio_pago=?, fecha_limite=?, monto=?, porcentaje_mora=cast(? as smallint) " +
                    "where id=?";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setDate(2, fechaInicioPago);
            preparedStatement.setDate(3, fechaLimite);
            preparedStatement.setDouble(4, monto);
            preparedStatement.setInt(5, porcentajeMora);
            preparedStatement.setInt(6, id);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class AporteData > update()");
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
     * removes input by identifier
     *
     * @param id: identifier input
     * @return true if removed success, else returns false
     */
    public boolean remove(int id) {
        try {
            String query = "delete from aporte where id=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class AporteData > remove()");
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
     * find by attribute one data selected
     * @param attribute the attribute to find by
     * @param data the attribute value
     * @return result query sql
     */
    public ResultSet findBy(String attribute, String data) {
        try {
            String query = "select * from aporte a where a." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
