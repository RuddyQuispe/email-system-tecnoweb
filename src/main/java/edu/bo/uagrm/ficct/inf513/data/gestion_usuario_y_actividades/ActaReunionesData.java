/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author stephani
 */
public class ActaReunionesData {
     private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public ActaReunionesData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Acta de Reunion
     * @param fechaReunion
     * @param descripcion 
     * @param ciEmpleado ci of the empleado
     * @return true  
     */
    public boolean create(Date fechaReunion, String descripcion, int ciEmpleado) {
        try {
            // string query structure
            String query = "insert into acta_reunion(fecha_reunion, descripcion, ci_empleado) values\n" +
                    "(?,?,?)"; 
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaReunion);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setInt(3, ciEmpleado);
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
 
    
    public ResultSet findAll() {
        try {
            String query = "select a.nro_acta , a.fecha_reunion, a.descripcion, get_name_by_userci(a.ci_empleado) as nombre_empleado from acta_reunion a;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

 

    
    public boolean update(int nroActa,  Date fechaReunion, String descripcion, int ciEmpleado) {
        try {
            // string query structure
            String query = "update acta_reunion set " +
                    "fecha_reunion=?, descripcion=?, ci_empleado=?" +
                    "where nro_acta=?;";
            // get object connection  
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaReunion);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setInt(3, ciEmpleado);
            preparedStatement.setInt(4, nroActa);
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


    public boolean remove(int nroActa) {
        try {
            String query = "delete from acta_reunion where nro_acta=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroActa);
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
