package edu.bo.uagrm.ficct.inf513.data.gestion_de_contabilidad;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

public class IngresoData {
    private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public IngresoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Ingreso
     * @param detalle detail of ingreso
     * @param fechaIngreso date of ingreso
     * @param monto incoming amount
     * @param ciEmpleado ci of the empleado
     * @return true if the ingreso was created successfully else return, false have an error
     */
    public boolean create(String detalle, Date fechaIngreso, double monto, int ciEmpleado) {
        try {
            // string query structure
            String query = "insert into ingreso(detalle, fecha_ingreso, monto, ci_empleado) values\n" +
                    "(?,?,?,?)";
            // get object connection to add Ingreso information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, detalle);
            preparedStatement.setDate(2, fechaIngreso);
            preparedStatement.setDouble(3, monto);
            preparedStatement.setInt(4, ciEmpleado);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class "+ this.getClass().getName()+ "> create()");
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
     * get all Ingreso data
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select i.nro_ingreso, i.detalle, i.fecha_ingreso, i.monto,  get_name_by_userci(i.ci_empleado) as nombre_empleado from ingreso i ;";
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
            String query = "select * from ingreso i where i." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update Ingreso data
     * @param nroIngreso
     * @param detalle
     * @param fechaIngreso
     * @param monto
     * @param ciEmpleado
     * @return true if updated success else return, false have an error
     */
    public boolean update(int nroIngreso, String detalle, Date fechaIngreso, double monto, int ciEmpleado) {
        try {
            // string query structure
            String query = "update ingreso set " +
                    "detalle=?, fecha_ingreso=?, monto=?, ci_empleado=?" +
                    "where nro_ingreso=?;";
            // get object connection to add Ingreso information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, detalle);
            preparedStatement.setDate(2, fechaIngreso);
            preparedStatement.setDouble(3, monto);
            preparedStatement.setInt(4, ciEmpleado);
            preparedStatement.setInt(5, nroIngreso);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " +this.getClass().getName()+"> update()");
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
     * delete Ingreso data
     * @param nroIngreso :nroIngreso Ingreso
     * @return true if removed success else return false
     */
    public boolean remove(int nroIngreso) {
        try {
            String query = "delete from ingreso where nro_ingreso=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroIngreso);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " + this.getClass().getName()+"> remove()");
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