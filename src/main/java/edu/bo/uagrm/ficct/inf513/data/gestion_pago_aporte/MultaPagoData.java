package edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MultaPagoData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public MultaPagoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /***
     * save the payment of a fine
     * @param nroPago pago identifier
     * @param idMulta multa identifier
     * @return true if was created, else return false
     */
    public boolean create(int nroPago, int idMulta) {
        try {
            String query = "insert into multa_pago(nro_pago, id_multa) values (?,?);";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            preparedStatement.setInt(2, idMulta);
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
     * returns all fines paid from a specific payment
     * @return result query sql
     */
    public ResultSet findAllByPago(int nroPago) {
        try {
            String query = "select mp.nro_pago, mp.id_multa , m.descripcion, m.monto from multa_pago mp, multa m where mp.id_multa = m.id and mp.nro_pago = "+nroPago +";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * removes a fine paid
     * @param nroPago : number of pago
     * @param idMulta : identifier of multa
     * @return true is removed, else return false
     */
    public boolean removeByPago(int nroPago, int idMulta) {
        try {
            String query = "delete from multa_pago where nro_pago=? and id_multa=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            preparedStatement.setInt(2, idMulta);
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
}
