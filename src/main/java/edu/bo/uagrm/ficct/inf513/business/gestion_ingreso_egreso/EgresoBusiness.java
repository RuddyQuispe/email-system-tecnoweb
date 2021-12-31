package edu.bo.uagrm.ficct.inf513.business.gestion_ingreso_egreso;

import edu.bo.uagrm.ficct.inf513.data.gestion_ingreso_egreso.EgresoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EgresoBusiness {
    private EgresoData egresoData;

    public EgresoBusiness() {
        this.egresoData = new EgresoData();
    }

    /**
     * create a new Egreso
     * @param parameters list of parameters
     * @return a message
     */
    public String createEgreso(List<String> parameters) {
        if (parameters.size() != 5) return "data Egreso incomplete";
        try {
            //TODO get ciEmpleado by empleado's name
            int ciEmpleado =  Integer.parseInt(parameters.get(4));
            boolean isCreatedEgreso = this.egresoData.create(parameters.get(0), Double.parseDouble(parameters.get(1)), DateString.StringToDateSQL(parameters.get(2)), parameters.get(3), ciEmpleado);
            return isCreatedEgreso ? "saved Egreso successfully" : "I have an error to create Egreso";
        } catch (ParseException e) {
            e.printStackTrace();
            return "I have an error to create Egreso";
        }
    }

    /**
     * get all data
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.egresoData.findAll();
        return this.getDataList(data);
    }

    /**
     * get all data by a specific attribute
     * @param parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findBy(List<String> parameters){
        ResultSet data = this.egresoData.findBy(parameters.get(0), parameters.get(1));
        return this.getDataList(data);
    }

    /**
     * update a egreso
     * @param parameters list of parameters
     * @return a message
     */
    public String updateEgreso(List<String> parameters) {
        if (parameters.size() != 6) return "data Egreso incomplete";
        try {
            ResultSet response = this.egresoData.findBy("nro_egreso", parameters.get(0));
            if (response == null) return "Error to search nro_egreso egreso";
            if (response.next()) {
                //TODO: call Empleado Method to search id by name
                int ciEmpleado =  Integer.parseInt(parameters.get(5));
                boolean isUpdatedEgreso = this.egresoData.update(Integer.parseInt(parameters.get(0)), parameters.get(1), Double.parseDouble(parameters.get(2)), DateString.StringToDateSQL(parameters.get(3)), parameters.get(4),ciEmpleado);
                return isUpdatedEgreso ? "updated Egreso successfully" : "I have an error to update Egreso";
            } else {
                return "Doesn't exists Egreso ";
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return "I have an error to update Egreso";
        }
    }

    /**
     * delete a specific Egreso
     * @param parameters list of parameters
     * @return a message
     */
    public String removeEgreso(List<String> parameters) {
        if (parameters.size() != 1) return "data Egreso incomplete";
        try {
            ResultSet response = this.egresoData.findBy("nro_egreso", parameters.get(0));
            if (response == null) return "Error to search nro_egreso egreso";
            if (response.next()) {
                boolean isRemovedEgreso = this.egresoData.remove(Integer.parseInt(parameters.get(0)));
                return isRemovedEgreso ? "removed Egreso successfully" : "I have an error to remove Egreso";
            } else {
                return "Doesn't exists Egreso ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to remove Egreso";
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
}