package edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.PagoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.SocioData;

public class KardexBusiness {
    private PagoData pagoData;

    public KardexBusiness() {
        this.pagoData = new PagoData();
    }

    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> getPaidAportesBySocio(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList<>();
        SocioData socioData = new SocioData();
        ResultSet socioResultSet = socioData.findBy("nombre", parameters.get(0));
        ArrayList<ArrayList<String>> socio = this.getDataList(socioResultSet);
        ResultSet contributionsPaid = this.pagoData.getPaidContributionsByPartner(Integer.parseInt(socio.get(1).get(0)));
        return this.getDataList(contributionsPaid);
    }

    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> getPaidMultasBySocio(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList<>();
        SocioData socioData = new SocioData();
        ResultSet socioResultSet = socioData.findBy("nombre", parameters.get(0));
        ArrayList<ArrayList<String>> socio = this.getDataList(socioResultSet);
        ResultSet contributionsPaid = this.pagoData.getPaidFinesByPartner(Integer.parseInt(socio.get(1).get(0)));
        return this.getDataList(contributionsPaid);
    }

    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> getUnpaidAportesBySocio(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList<>();
        SocioData socioData = new SocioData();
        ResultSet socioResultSet = socioData.findBy("nombre", parameters.get(0));
        ArrayList<ArrayList<String>> socio = this.getDataList(socioResultSet);
        ResultSet contributionsPaid = this.pagoData.getUnpaidContributionsByPartner(Integer.parseInt(socio.get(1).get(0)));
        return this.getDataList(contributionsPaid);
    }

    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> getUnpaidMultasBySocio(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList<>();
        SocioData socioData = new SocioData();
        ResultSet socioResultSet = socioData.findBy("nombre", parameters.get(0));
        ArrayList<ArrayList<String>> socio = this.getDataList(socioResultSet);
        ResultSet contributionsPaid = this.pagoData.getUnpaidFinesByPartner(Integer.parseInt(socio.get(1).get(0)));
        return this.getDataList(contributionsPaid);
    }

    /**
     * Get all rows of a table
     */
    private ArrayList<ArrayList<String>> getDataList(ResultSet rs) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            ResultSetMetaData metadata = rs.getMetaData();
            int quantityColumns = metadata.getColumnCount();
            int j = 0;
            result.add(new ArrayList<String>());
            for (int i = 1; i <= quantityColumns; i++) {
                result.get(0).add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                j++;
                result.add(new ArrayList<String>());
                for (int i = 1; i <= quantityColumns; i++) {
                    result.get(j).add(rs.getString(i));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
