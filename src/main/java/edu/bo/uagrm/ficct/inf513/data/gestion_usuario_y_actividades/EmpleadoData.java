/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades;
import java.sql.*;

/**
 *
 * @author stephani
 */
public class EmpleadoData extends UsuarioData{
    /**
     * @Constructor
     */
    public EmpleadoData() {
        super();
    }
 
    public boolean create(int ci, String nombre, String telefono, String email, String contrasenia,
                          String direccion, Date fechaInicio, Date fechaFin) {
        try {
            // string query structure
            boolean createdUser = super.create(ci, nombre, telefono, email, contrasenia, direccion, "E");
            if (!createdUser) return false;
            String query = "insert into empleado(ci_empleado, fecha_inicio, fecha_fin) values(?, ?, ?);";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ci);
            preparedStatement.setDate(2, fechaInicio); 
            preparedStatement.setDate(3, fechaFin);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class EmpleadoData give: "
                        + "Ocurrio un error al insertar un empleado create()");
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
            String query = "select e.ci_empleado , e.fecha_inicio, e.fecha_fin from empleado e;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean update(int ci, String nombre, String telefono, String email, String contrasenia, boolean estado, String direccion, Date fechaFin){
        try {
            // string query structure 
            boolean updateUser = super.update(ci, nombre, telefono, email, contrasenia, estado, direccion, "E");
            if (!updateUser) return false;
            String query = "update empleado " +
                    "set fechaFin=?" + "where ci=?";
  
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query); 
            preparedStatement.setDate(1, fechaFin);
            preparedStatement.setInt(2, ci);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class EmpleadoData > update()");
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
