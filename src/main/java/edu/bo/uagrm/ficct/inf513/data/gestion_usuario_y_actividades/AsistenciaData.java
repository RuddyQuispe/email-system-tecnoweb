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
public class AsistenciaData {
     private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public AsistenciaData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Asistencia
     * @param fecha_actividad
     * @param actividad 
     * @return true  
     */
    public boolean create(Date fechaActividad, String actividad ) {
        try {
            // string query structure
            String query = "insert into asistencia(fecha_actividad, actividad) values\n" +
                    "(?,?)"; 
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaActividad);
            preparedStatement.setString(2, actividad);
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
            String query = "select a.id , a.fecha_actividad, a.actividad from asistencia a;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

 

    
    public boolean update(int id,  Date fecha_actividad, String actividad) {
        try {
            // string query structure
            String query = "update asistencia set " +
                    "fecha_actividad=?, actividad=?" +
                    "where id=?;";
            // get object connection  
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fecha_actividad);
            preparedStatement.setString(2, actividad); 
            preparedStatement.setInt(3, id);
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


    public boolean remove(int id) {
        try {
            String query = "delete from asistencia where id=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
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
