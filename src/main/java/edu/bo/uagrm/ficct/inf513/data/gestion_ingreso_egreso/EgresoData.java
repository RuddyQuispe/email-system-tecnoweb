package edu.bo.uagrm.ficct.inf513.data.gestion_ingreso_egreso;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

public class EgresoData {
    private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public EgresoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Egreso
     * @param detalle detail of Egreso
     * @param monto incoming amount
     * @param fechaEgreso date of Egreso
     * @param actorReceptor the entity that receives
     * @param ciEmpleado ci of the empleado
     * @return true if the Egreso was created successfully else return, false have an error
     */
    public boolean create(String detalle, double monto, Date fechaEgreso, String actorReceptor,int ciEmpleado) {
        try {
            // string query structure
            String query = "insert into egreso(detalle,  monto, fecha_egreso, actor_receptor, ci_empleado) values\n" +
                    "(?,?,?,?,?)";
            // get object connection to add egreso information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, detalle);
            preparedStatement.setDouble(2, monto);
            preparedStatement.setDate(3, fechaEgreso);
            preparedStatement.setString(4, actorReceptor);
            preparedStatement.setInt(5, ciEmpleado);
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
     * get all Egreso data
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select e.nro_egreso, e.detalle, e.monto, e.fecha_egreso, e.actor_receptor ,get_name_by_userci(e.ci_empleado) as nombre_empleado from egreso e ;";
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
            String query = "select * from egreso e where e." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update egreso data
     * @param nroEgreso
     * @param detalle
     * @param monto
     * @param fechaEgreso
     * @param actorReceptor
     * @param ciEmpleado
     * @return true if updated success else return, false have an error
     */
    public boolean update(int nroEgreso, String detalle, double monto, Date fechaEgreso, String actorReceptor, int ciEmpleado) {
        try {
            // string query structure
            String query = "update egreso set " +
                    "detalle=?, monto=?, fecha_egreso=?, actor_receptor=?, ci_empleado=?" +
                    "where nro_egreso=?;";
            // get object connection to add Ingreso information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, detalle);
            preparedStatement.setDouble(2, monto);
            preparedStatement.setDate(3, fechaEgreso);
            preparedStatement.setString(4, actorReceptor);
            preparedStatement.setInt(5, ciEmpleado);
            preparedStatement.setInt(6, nroEgreso);
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
     * delete egreso data
     * @param nroEgreso :nroEgreso
     * @return true if removed success else return false
     */
    public boolean remove(int nroEgreso) {
        try {
            String query = "delete from egreso where nro_egreso=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroEgreso);
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