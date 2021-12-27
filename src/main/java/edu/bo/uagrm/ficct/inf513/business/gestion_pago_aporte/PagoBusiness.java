package edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.PagoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PagoBusiness {
    private PagoData pagoData;

    public PagoBusiness() {
        this.pagoData = new PagoData();
    }

    /**
     * create a new Pago
     * @param parameters list of parameters
     * @return a message
     */
    public String createPago(List<String> parameters) {
        if (parameters.size() != 4) return "data Pago incomplete";
        try {
            //TODO: call Secretaria and Socio Method to search id by name
            int ciSocio = Integer.parseInt(parameters.get(2));
            int ciSecretaria =  Integer.parseInt(parameters.get(3));
            boolean isCreatedPago = this.pagoData.create(DateString.StringToDateSQL(parameters.get(0)), parameters.get(1), ciSocio, ciSecretaria);
            return isCreatedPago ? "saved Pago successfully" : "I have an error to create Pago";
        } catch (ParseException e) {
            e.printStackTrace();
            return "I have an error to create Pago";
        }
    }

    /**
     * get all data
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.pagoData.findAll();
        return this.getDataList(data);
    }

    /**
     * get all data by a specific attribute
     * @param parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findBy(List<String> parameters){
        ResultSet data = this.pagoData.findBy(parameters.get(0), parameters.get(1));
        return this.getDataList(data);
    }

    /**
     * update a pago
     * @param parameters list of parameters
     * @return a message
     */
    public String updatePago(List<String> parameters) {
        if (parameters.size() != 5) return "data Pago incomplete";
        try {
            ResultSet response = this.pagoData.findBy("nro_pago", parameters.get(0));
            if (response == null) return "Error to search nro_pago Pago";
            if (response.next()) {
                //TODO: call Secretaria and Socio Method to search id by name
                int ciSocio = Integer.parseInt(parameters.get(3));
                int ciSecretaria =  Integer.parseInt(parameters.get(4));
                boolean isUpdatedPago = this.pagoData.update(Integer.parseInt(parameters.get(0)), DateString.StringToDateSQL(parameters.get(1)), parameters.get(2), ciSocio, ciSecretaria);
                return isUpdatedPago ? "updated Pago successfully" : "I have an error to update Pago";
            } else {
                return "Doesn't exists Pago ";
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return "I have an error to update Pago";
        }
    }

    /**
     * delete a specific pago
     * @param parameters list of parameters
     * @return a message
     */
    public String removePago(List<String> parameters) {
        if (parameters.size() != 1) return "data Pago incomplete";
        try {
            ResultSet response = this.pagoData.findBy("nro_pago", parameters.get(0));
            if (response == null) return "Error to search nro_pago Pago";
            if (response.next()) {
                boolean isRemovedPago = this.pagoData.remove(Integer.parseInt(parameters.get(0)));
                return isRemovedPago ? "removed Pago successfully" : "I have an error to remove Pago";
            } else {
                return "Doesn't exists Pago ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to remove Pago";
        }
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
    public static void main(String[] args) {
        PagoBusiness Pago = new PagoBusiness();
        ArrayList<ArrayList<String>> response = Pago.findAll();
        System.out.println(response);
    }
}
