package edu.bo.uagrm.ficct.inf513.data.gestion_de_pago_de_aportes;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AportePagoData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public AportePagoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * save the payment of a contribution
     *
     * @param nroPago   pago identifier
     * @param idAporte  Aporte identifier
     * @param montoMora extra amount to pay for late payment
     * @return true if was created, else return false
     */
    public boolean create(int nroPago, int idAporte, double montoMora) {
        try {
            String query = "insert into aporte_pago(nro_pago, id_aporte, monto_mora) values (?,?,?);";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            preparedStatement.setInt(2, idAporte);
            preparedStatement.setDouble(3, montoMora);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " + this.getClass().getName() + " > create()");
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
     * returns all contributions paid from a specific payment
     *
     * @return result query sql
     */
    public ResultSet findAllByPago(int nroPago) {
        try {
            String query = "select ap.nro_pago ,ap.id_aporte , a.descripcion , a.monto, ap.monto_mora from aporte_pago ap, aporte a where ap.id_aporte = a.id and ap.nro_pago = " + nroPago + ";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * removes a contribution paid
     *
     * @param nroPago  : number of pago
     * @param idAporte : identifier of Aporte
     * @return true is removed, else return false
     */
    public boolean removeByPago(int nroPago, int idAporte) {
        try {
            String query = "delete from aporte_pago where nro_pago=? and id_aporte=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            preparedStatement.setInt(2, idAporte);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " + this.getClass().getName() + " > removeByPago()");
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
     * get count pagos by an aporte
     *
     * @param idAporte : aporte identifier
     * @return result sql query
     */
    public ResultSet getCountPagoByAporte(int idAporte) {
        try {
            String query = "select count(distinct ap.nro_pago) as count_pagos from aporte_pago ap where ap.id_aporte=" + idAporte + ";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
