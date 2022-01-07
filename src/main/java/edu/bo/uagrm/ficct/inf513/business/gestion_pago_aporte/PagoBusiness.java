package edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.AporteData;
import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.AportePagoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.MultaPagoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.PagoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.SocioData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.EmpleadoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PagoBusiness {
    private PagoData pagoData;
    private AportePagoData aportePagoData;
    private MultaPagoData multaPagoData;

    public PagoBusiness() {
        this.pagoData = new PagoData();
        this.aportePagoData = new AportePagoData();
        this.multaPagoData = new MultaPagoData();
    }

    /**
     * create a new Pago
     * @param parameters list of parameters
     * @return a message
     */
    public String createPago(List<String> parameters) {
        if (parameters.size() != 4) return "data Pago incomplete";
        try {
            SocioData socioData = new SocioData();
            ResultSet resultSet = socioData.findBy("nombre", parameters.get(2));
            ArrayList<ArrayList<String>> data = this.getDataList(resultSet);
            int ciSocio = Integer.parseInt(data.get(1).get(0));

            EmpleadoData empleadoData = new EmpleadoData();
            resultSet = empleadoData.findBy("nombre", parameters.get(3));
            data = this.getDataList(resultSet);
            int ciEmpleado =  Integer.parseInt(data.get(1).get(0));

            boolean isCreatedPago = this.pagoData.create(DateString.StringToDateSQL(parameters.get(0)), parameters.get(1), ciSocio, ciEmpleado);
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
                SocioData socioData = new SocioData();
                ResultSet resultSet = socioData.findBy("nombre", parameters.get(3));
                ArrayList<ArrayList<String>> data = this.getDataList(resultSet);
                int ciSocio = Integer.parseInt(data.get(1).get(0));

                EmpleadoData empleadoData = new EmpleadoData();
                resultSet = empleadoData.findBy("nombre", parameters.get(4));
                data = this.getDataList(resultSet);
                int ciEmpleado =  Integer.parseInt(data.get(1).get(0));

                boolean isUpdatedPago = this.pagoData.update(Integer.parseInt(parameters.get(0)), DateString.StringToDateSQL(parameters.get(1)), parameters.get(2), ciSocio, ciEmpleado);
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
    //Aporte Pago methods
    /**
     * create a new AportePago
     * @param parameters list of parameters
     * @return a message
     */
    public String createAportePago(List<String> parameters) {
        if (parameters.size() != 2) return "data AportePago incomplete";
        AporteData aporteData = new AporteData();
        ResultSet findBy = aporteData.findBy("id", parameters.get(1));
        ArrayList<ArrayList<String>> data = getDataList(findBy);
        Calendar dateNow = Calendar.getInstance();
        Calendar fechaLimite = DateString.StringToDate(data.get(1).get(4));
        System.out.println("fecha actual: "+ dateNow.toString()+ "\nfecha limite: "+ fechaLimite.toString());
        double montoMora = 0;
        if(dateNow.after(fechaLimite)){ //time is over
            double monto = Double.parseDouble(data.get(1).get(3));
            int porcentajeMora = Integer.parseInt(data.get(1).get(5));
            montoMora = (monto*porcentajeMora)/100;
        }
        boolean isCreated = this.aportePagoData.create(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)), montoMora);
        return isCreated ? "saved Aporte Pago successfully" : "I have an error to create Aporte Pago";
    }
    /**
     * get all data of a aporte pago
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAllAportePago(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList();
        ResultSet data = this.aportePagoData.findAllByPago(Integer.parseInt(parameters.get(0)));
        return this.getDataList(data);
    }

    /**
     * delete a specific aporte pago
     * @param parameters list of parameters
     * @return a message
     */
    public String removeAportePago(List<String> parameters) {
        if (parameters.size() != 2) return "data AportePago incomplete";
        boolean isRemoved = this.aportePagoData.removeByPago(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
        return isRemoved ? "removed AportePago successfully" : "I have an error to remove AportePago";
    }

    //Multa Pago methods
    /**
     * create a new MultaPago
     * @param parameters list of parameters
     * @return a message
     */
    public String createMultaPago(List<String> parameters) {
        if (parameters.size() != 2) return "data Pago incomplete";
        boolean isCreated = this.multaPagoData.create(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
        return isCreated ? "saved Multa Pago successfully" : "I have an error to create Multa Pago";
    }
    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAllMultaPago(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList();
        ResultSet data = this.multaPagoData.findAllByPago(Integer.parseInt(parameters.get(0)));
        return this.getDataList(data);
    }
    /**
     * delete a specific aporte pago
     * @param parameters list of parameters
     * @return a message
     */
    public String removeMultaPago(List<String> parameters) {
        if (parameters.size() != 2) return "data MultaPago incomplete";
        boolean isRemoved = this.multaPagoData.removeByPago(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
        return isRemoved ? "removed MultaPago successfully" : "I have an error to remove MultaPago";
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