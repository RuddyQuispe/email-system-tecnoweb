package edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte;
import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

public class PagoData {
    private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public PagoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Pago
     * @param fechaPago date of Pago
     * @param comprobante
     * @param ciSocio ci of the socio
     * @param ciEmpleado ci of the empleado
     * @return true if the pago was created successfully else return, false have an error
     */
    public boolean create(Date fechaPago, String comprobante, int ciSocio, int ciEmpleado) {
        try {
            // string query structure
            String query = "insert into pago(fecha_pago, monto_total, comprobante, ci_socio, ci_empleado)"
                    + " values(?,?,?,?,?)";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaPago);
            preparedStatement.setInt(2, 0);
            preparedStatement.setString(3, comprobante);
            preparedStatement.setInt(4, ciSocio);
            preparedStatement.setInt(5, ciEmpleado);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > create()");
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
     * get all Pago data
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select p.nro_pago , p.fecha_pago , p.monto_total ,p.comprobante ,get_name_by_userci(p.ci_socio) as nombre_socio, get_name_by_userci(p.ci_empleado) as nombre_empleado  \n" +
                    "from pago p;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
            String query = "select * from pago p where p." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update Pago data
     * @param nroPago
     * @param fechaPago
     * @param comprobante
     * @param ciSocio
     * @param ciEmpleado
     * @return true if updated success else return, false have an error
     */
    public boolean update(int nroPago, Date fechaPago, String comprobante, int ciSocio, int ciEmpleado) {
        try {
            // string query structure
            String query = "update pago set " +
                    "fecha_pago=?, comprobante=?, ci_socio=?, ci_empleado=?" +
                    "where nro_pago=?;";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaPago);
            preparedStatement.setString(2, comprobante);
            preparedStatement.setInt(3, ciSocio);
            preparedStatement.setInt(4, ciEmpleado);
            preparedStatement.setInt(5, nroPago);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > update()");
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
     * delete Pago data
     * @param nroPago :nroPago Pago
     * @return true if removed success else return false
     */
    public boolean remove(int nroPago) {
        try {
            String query = "delete from pago where nro_pago=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > remove()");
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
