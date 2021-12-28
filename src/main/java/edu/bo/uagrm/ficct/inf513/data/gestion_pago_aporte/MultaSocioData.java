package edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-27 19:53
 */
public class MultaSocioData {
    private ConnectionDB connection;

    /**
     * @Constructor
     */
    public MultaSocioData() {
        this.connection = ConnectionDB.getInstance();
    }

    public boolean create(int ciSocio, int idMulta) {
        try {
            // string query structure
            String query = "insert into multa_socio(ci_socio, id_multa) values (?,?);";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ciSocio);
            preparedStatement.setInt(2, idMulta);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class MultaSocioData > create()");
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
     * get all partner with multa
     *
     * @return result query sql
     */
    public ResultSet findByIdMulta(int idMulta) {
        try {
            String query = "select s.ci_socio, u.nombre, " +
                    "case s.tipo_socio when '1' then 'pasivo' else 'activo' end as tipo_socio, s.nro_puesto " +
                    "from multa_socio ms, multa m, socio s, usuario u " +
                    "where ms.ci_socio=s.ci_socio and ms.id_multa=m.id and s.ci_socio=u.ci and m.id=" + idMulta + ";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * removes partner into multa register
     *
     * @param ciSocio : identifier Socio
     * @param idMulta : id multa assigned
     * @return true is removed, else return false
     */
    public boolean removeSocioByMulta(int ciSocio, int idMulta) {
        try {
            String query = "delete from multa_socio where ci_socio=? and id_multa=?";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, ciSocio);
            preparedStatement.setInt(2, idMulta);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class " + this.getClass().getName() + " > removeSocioByMulta()");
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
