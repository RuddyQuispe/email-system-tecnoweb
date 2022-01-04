/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author stephani
 */
public class AsistenciaSocioData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public AsistenciaSocioData() {
        this.connection = ConnectionDB.getInstance();
    }

    /***
     * @param idAsistencia 
     * @param ciSocio  
     * @return true 
     */
    public boolean createAsistenciaSocio(int idAsistencia, int ciSocio) {
        try {
            String query = "insert into asistencia_socio(id_asistencia, ci_socio) values (?,?);";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idAsistencia);
            preparedStatement.setInt(2, ciSocio);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class "+ this.getClass().getName()+ " > create()");
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
     * @param idAsistencia
     * @return result query sql
     */
    public ResultSet findAllByAsistencia(int idAsistencia) {
        try {
            String query = "select as.id_asistencia, a.fecha_actividad , a.actividad, as.ci_socio from asistencia_socio as, asistencia a where as.id_asistencia = a.id and as.id_asistencia = "+idAsistencia +";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 
     * @param idAsistencia  
     * @param ciSocio  
     * @return true  
     */
    public boolean removeAsistenciaSocio(int idAsistencia, int ciSocio) {
        try {
            String query = "delete from asistencia_socio where id_asistencia=? and ci_socio=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idAsistencia);
            preparedStatement.setInt(2, ciSocio);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " + this.getClass().getName() + " > remove()");
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
