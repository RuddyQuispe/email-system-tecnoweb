package edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 12/9/21 20:22
 */
public class UsuarioData {
    protected ConnectionDB connection;

    /**
     * @Constructor
     */
    public UsuarioData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * Create new user general
     *
     * @param ci       :       int ci user
     * @param name     :     first and last name user
     * @param phone    :    phone contact
     * @param email    :    email (gmail, yahoo, gmx, others.)
     * @param password : password encrypted
     * @param address  :  address description home user
     * @return true if saved successfully; else return false an error occurred.
     */
    public boolean create(int ci, String name, String phone, String email, String password, String address, String userType) {
        try {
            // string query structure
            String query = "insert into usuario(ci, nombre, telefono, email, estado, contraseña, direccion, tipo_usuario)"
                    + " values(?,?,?,?,?,?,?,?)";
            // get object connection to add user information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ci);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, "1");
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, userType);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class UserData give: "
                        + "Ocurrio un error al insertar un usuario guardar()");
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
            String query = "select ci, nombre, telefono, email ,estado, contrasenia, direccion, tipo_usuario from usuario";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update user data info
     *
     * @param ci       :ci user identity
     * @param name     :name user
     * @param phone    :phone user
     * @param email    :email
     * @param password :password encrypted
     * @param status   :true enable or false disabled
     * @param address  :address user
     * @param userType :"P" partner, "S" secretary, "D"Directive
     * @return true if updated success else return, false have an error
     */
    public boolean update(int ci, String name, String phone, String email, String password, boolean status, String address, String userType) {
        try {
            // string query structure
            String query = "update usuario set " +
                    "nombre=?, telefono=?, email=?, estado=?, contraseña=?, direccion=? " +
                    "where ci=? and tipo_usuario=?;";
            // get object connection to add user information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, status ? "1" : "0");
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, address);
            preparedStatement.setInt(7, ci);
            preparedStatement.setString(8, userType);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class UserData.java dice: "
                        + "Ocurrio un error al insertar un usuario guardar()");
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
     * delete user data
     *
     * @param ci :ci user
     * @return true if removed success else return false
     */
    public boolean remove(int ci) {
        try {
            String query = "delete from usuario where ci=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ci);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class UserData.java dice: "
                        + "Ocurrio un error al insertar un usuario guardar()");
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
