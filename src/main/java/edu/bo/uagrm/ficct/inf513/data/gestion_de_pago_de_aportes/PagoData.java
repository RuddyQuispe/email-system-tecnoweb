package edu.bo.uagrm.ficct.inf513.data.gestion_de_pago_de_aportes;
import edu.bo.uagrm.ficct.inf513.data.conection_database.ConnectionDB;

import java.sql.*;

public class PagoData {
    private final ConnectionDB connection;

    /**
     * @Constructor
     */
    public PagoData() {
        this.connection = ConnectionDB.getInstance();
    }

    /**
     * create a new Pago
     * @param fechaPago date of Pago
     * @param comprobante
     * @param ciSocio ci of the socio
     * @param ciEmpleado ci of the empleado
     * @return true if the pago was created successfully else return, false have an error
     */
    public boolean create(Date fechaPago, String comprobante, int ciSocio, int ciEmpleado) {
        try {
            // string query structure
            String query = "insert into pago(fecha_pago, monto_total, comprobante, ci_socio, ci_empleado)"
                    + " values(?,?,?,?,?)";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaPago);
            preparedStatement.setInt(2, 0);
            preparedStatement.setString(3, comprobante);
            preparedStatement.setInt(4, ciSocio);
            preparedStatement.setInt(5, ciEmpleado);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > create()");
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
     * get all Pago data
     * @return result query sql
     */
    public ResultSet findAll() {
        try {
            String query = "select p.nro_pago , p.fecha_pago , p.monto_total ,p.comprobante ,get_name_by_userci(p.ci_socio) as nombre_socio, get_name_by_userci(p.ci_empleado) as nombre_empleado  \n" +
                    "from pago p;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * find by attribute one data selected
     * @param attribute the attribute to find by
     * @param data the attribute value
     * @return result query sql
     */
    public ResultSet findBy(String attribute, String data) {
        try {
            String query = "select * from pago p where p." + attribute + " ='" + data + "';";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update Pago data
     * @param nroPago
     * @param fechaPago
     * @param comprobante
     * @param ciSocio
     * @param ciEmpleado
     * @return true if updated success else return, false have an error
     */
    public boolean update(int nroPago, Date fechaPago, String comprobante, int ciSocio, int ciEmpleado) {
        try {
            // string query structure
            String query = "update pago set " +
                    "fecha_pago=?, comprobante=?, ci_socio=?, ci_empleado=?" +
                    "where nro_pago=?;";
            // get object connection to add Pago information to make
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, fechaPago);
            preparedStatement.setString(2, comprobante);
            preparedStatement.setInt(3, ciSocio);
            preparedStatement.setInt(4, ciEmpleado);
            preparedStatement.setInt(5, nroPago);
            // execute query with its data
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > update()");
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
     * delete Pago data
     * @param nroPago :nroPago Pago
     * @return true if removed success else return false
     */
    public boolean remove(int nroPago) {
        try {
            String query = "delete from pago where nro_pago=?;";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, nroPago);
            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("error in: Class PagoData > remove()");
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
     * return paid contributions from a partner
     * @param ciSocio the ci of socio
     * @return result query sql
     */
    public ResultSet getPaidContributionsByPartner(int ciSocio) {
        try {
            String query = "select p.nro_pago ,get_name_by_userci(ci_empleado) as nombre_empleado, a.descripcion, a.fecha_inicio_pago , a.fecha_limite , p.fecha_pago ,a.monto , a.porcentaje_mora ,ap.monto_mora, sum(a.monto+ap.monto_mora) as aporte_subtotal, p.monto_total \n" +
                    "from pago p, aporte a, aporte_pago ap, socio s \n" +
                    "where p.nro_pago = ap.nro_pago and a.id = ap.id_aporte and p.ci_socio = s.ci_socio and s.ci_socio = "+ ciSocio +
                    "group by p.nro_pago, a.descripcion, a.fecha_inicio_pago , a.fecha_limite, a.monto, a.porcentaje_mora ,ap.monto_mora;";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return paid fines from a partner
     * @param ciSocio the ci of socio
     * @return result query sql
     */
    public ResultSet getPaidFinesByPartner(int ciSocio) {
        try {
            String query = "select p.nro_pago, get_name_by_userci(ci_empleado) as nombre_empleado, m.descripcion, m.monto, p.monto_total \n" +
                    "from pago p , multa m , multa_pago mp , socio s \n" +
                    "where p.nro_pago = mp.nro_pago and m.id = mp.id_multa and p.ci_socio = s.ci_socio and s.ci_socio = "+ ciSocio + ";";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return unpaid contributions from a partner
     * @param ciSocio the ci of socio
     * @return result query sql
     */
    public ResultSet getUnpaidContributionsByPartner(int ciSocio) {
        try {
            String query = "select * from aporte a where a.id not in (\n" +
                                "select ap.id_aporte\n" +
                                "from socio s , pago p , aporte_pago ap \n" +
                                "where s.ci_socio = p.ci_socio  and p.nro_pago = ap.nro_pago and s.ci_socio =  "+ ciSocio + ");";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return unpaid fines from a partner
     * @param ciSocio the ci of socio
     * @return result query sql
     */
    public ResultSet getUnpaidFinesByPartner(int ciSocio) {
        try {
            String query = "select * \n" +
                    "from multa m \n" +
                    "where m.id in (select ms.id_multa from multa_socio ms where ci_socio = "+ ciSocio + ") \n" +
                                    "and m.id not in (select mp.id_multa\n" +
                                    "from socio s, pago p, multa_pago mp\n" +
                                    "where s.ci_socio = p.ci_socio and p.nro_pago = mp.nro_pago and s.ci_socio  = "+ ciSocio + ");";
            Statement statement = this.connection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
