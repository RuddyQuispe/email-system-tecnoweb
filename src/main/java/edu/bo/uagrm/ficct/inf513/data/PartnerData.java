package edu.bo.uagrm.ficct.inf513.data;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 14/9/21 11:31
 */
public class PartnerData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public PartnerData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create data partner only
     *
     * @param ciPartner       :ci user
     * @param affiliationDate :date affiliation into association market 4 october
     * @param storeNo         :store no assigned to partner
     * @return true if saved successfully else false if it has a trouble
     */
    public boolean create(int ciPartner, Date affiliationDate, int storeNo, String typePartner) {
        try {
            // string query structure
            String query = "insert into socio " +
                    "(ci_socio, fecha_afiliacion, nro_puesto, tipo_socio) values " +
                    "(?,?,?,?);";
            // get object connection to add user information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ciPartner);
            preparedStatement.setDate(2, affiliationDate);
            preparedStatement.setInt(3, storeNo);
            preparedStatement.setString(4, typePartner);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Class PartnerData give: "
                        + "Ocurrio un error al insertar un usuario socio()");
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
     *
     * @param attribute :attribute
     * @param data      :data info
     * @return result query sql
     */
    public ResultSet findBy(String attribute, String data) {
        try {
            String query = "select * from socio s where s." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * update data to partner user
     *
     * @param ciPartner       :ci partner user
     * @param affiliationDate :affiliation date begin partner
     * @param storeNo         :store no assigned
     * @param typePartner     :type partner (1 active or 2 passive)
     * @return true if updated else return false
     */
    public boolean update(int ciPartner, Date affiliationDate, int storeNo, String typePartner) {
        try {
            // string query structure
            String query = "update socio set " +
                    "fecha_afiliacion=?, nro_puesto=?, tipo_socio=? " +
                    "where ci_socio=?;";
            // get object connection to add user information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, affiliationDate);
            preparedStatement.setInt(2, storeNo);
            preparedStatement.setString(3, typePartner);
            preparedStatement.setInt(4, ciPartner);
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
     * @param ciPartner :ci user
     * @return true if removed success else return false
     */
    public boolean remove(int ciPartner) {
        try {
            String query = "delete from socio where ci_socio=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ciPartner);
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
