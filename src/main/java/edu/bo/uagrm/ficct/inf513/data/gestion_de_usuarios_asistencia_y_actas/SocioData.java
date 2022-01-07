package edu.bo.uagrm.ficct.inf513.data.gestion_de_usuarios_asistencia_y_actas;

import java.sql.*;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-25 16:51
 */
public class SocioData extends UsuarioData {

    /**
     * @Constructor
     */
    public SocioData() {
        super();
    }

    /*
     * create new account partner
     * @param ci : ci user
     * @param nombre : first and last name user
     * @param telefono : phone contact
     * @param email : email (gmail, yahoo, gmx, others.)
     * @param contrasenia : password encrypted
     * @param direccion : address description home user
     * @param fechaAfiliacion : date registered into associate
     * @param nroPuesto : shop No. assigned into market
     * @param fechaInicio : date initialize
     * @return true if saved successfully; else return false an error occurred.
     */
    public boolean create(int ci, String nombre, String telefono, String email, String contrasenia,
                          String direccion, Date fechaAfiliacion, int nroPuesto, String tipoSocio, Date fechaInicio) {
        try {
            // string query structure
            boolean createdUser = super.create(ci, nombre, telefono, email, contrasenia, direccion, "S");
            if (!createdUser) return false;
            String query = "insert into socio(ci_socio, fecha_afiliacion, nro_puesto, tipo_socio, fecha_inicio) values(?, ?, ?, ?, ?);";
            // get object connection to add user information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ci);
            preparedStatement.setDate(2, fechaAfiliacion);
            preparedStatement.setInt(3, nroPuesto);
            preparedStatement.setString(4, tipoSocio);
            preparedStatement.setDate(5, fechaInicio);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class SocioData give: "
                        + "Ocurrio un error al insertar un socio create()");
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
     * get find by any attribute assigned a data
     *
     * @param attribute: attribute of table user
     * @param data:      info of attribute
     * @return list result
     */
    public ResultSet findBy(String attribute, String data) {
        try {
            String query = "select u.ci, u.nombre, u.telefono, u.email, u.estado, u.direccion, u.tipo_usuario " +
                    "from usuario u " +
                    "where u." + attribute + "='" + data + "' and u.tipo_usuario='S';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get count all socios registered into database
     *
     * @return
     */
    public int getCountSocio() {
        try {
            String query = "select count(distinct s.ci_socio) as count_socios from socio s;";
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("count_socios"));
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public ResultSet findAll() {
        try {
            String query = "select u.ci, u.nombre, u.telefono, u.email, u.estado, u.contraseña, u.direccion, s.fecha_afiliacion, s.nro_puesto,  s.tipo_socio, s.fecha_inicio \n" +
                    "from usuario u , socio s \n" +
                    "where u.ci = s.ci_socio ;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean update(int ci, String nombre, String telefono, String email, boolean estado,String contrasenia, String direccion, int nroPuesto ){
        try {
            //select u.ci, u.nombre, u.telefono, u.email, u.estado, u.contraseña, u.direccion,  s.nro_puesto
            // string query structure 
            boolean updateUser = super.update(ci, nombre, telefono, email, contrasenia, estado, direccion, "S");
            if (!updateUser) return false;
            String query = "update socio " +
                    "set nro_puesto=?" + "where ci_socio=?";
  
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query); 
            preparedStatement.setInt(1, nroPuesto);
            preparedStatement.setInt(2, ci);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class SocioData > update()");
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
