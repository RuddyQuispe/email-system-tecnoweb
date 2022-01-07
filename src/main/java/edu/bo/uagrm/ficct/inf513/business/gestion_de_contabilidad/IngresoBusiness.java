package edu.bo.uagrm.ficct.inf513.business.gestion_de_contabilidad;

import edu.bo.uagrm.ficct.inf513.data.gestion_de_contabilidad.IngresoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class IngresoBusiness {
    private IngresoData ingresoData;

    public IngresoBusiness() {
        this.ingresoData = new IngresoData();
    }

    /**
     * create a new Ingreso
     * @param parameters list of parameters
     * @return a message
     */
    public String createIngreso(List<String> parameters) {
        if (parameters.size() != 4) return "data Ingreso incomplete";
        try {
            //TODO get ciEmpleado by empleado's name
            int ciEmpleado =  Integer.parseInt(parameters.get(3));
            boolean isCreatedIngreso = this.ingresoData.create(parameters.get(0),DateString.StringToDateSQL(parameters.get(1)), Double.parseDouble(parameters.get(2)), ciEmpleado);
            return isCreatedIngreso ? "saved Ingreso successfully" : "I have an error to create Ingreso";
        } catch (ParseException e) {
            e.printStackTrace();
            return "I have an error to create Ingreso";
        }
    }

    /**
     * get all data
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.ingresoData.findAll();
        return this.getDataList(data);
    }

    /**
     * get all data by a specific attribute
     * @param parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findBy(List<String> parameters){
        ResultSet data = this.ingresoData.findBy(parameters.get(0), parameters.get(1));
        return this.getDataList(data);
    }

    /**
     * update a ingreso
     * @param parameters list of parameters
     * @return a message
     */
    public String updateIngreso(List<String> parameters) {
        if (parameters.size() != 5) return "data Ingreso incomplete";
        try {
            ResultSet response = this.ingresoData.findBy("nro_ingreso", parameters.get(0));
            if (response == null) return "Error to search nro_ingreso ingreso";
            if (response.next()) {
                //TODO: call Empleado Method to search id by name
                int ciEmpleado =  Integer.parseInt(parameters.get(4));
                boolean isUpdatedIngreso = this.ingresoData.update(Integer.parseInt(parameters.get(0)), parameters.get(1), DateString.StringToDateSQL(parameters.get(2)), Double.parseDouble(parameters.get(3)), ciEmpleado);
                return isUpdatedIngreso ? "updated Ingreso successfully" : "I have an error to update Ingreso";
            } else {
                return "Doesn't exists Ingreso ";
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return "I have an error to update Ingreso";
        }
    }

    /**
     * delete a specific Ingreso
     * @param parameters list of parameters
     * @return a message
     */
    public String removeIngreso(List<String> parameters) {
        if (parameters.size() != 1) return "data Ingreso incomplete";
        try {
            ResultSet response = this.ingresoData.findBy("nro_ingreso", parameters.get(0));
            if (response == null) return "Error to search nro_ingreso ingreso";
            if (response.next()) {
                boolean isRemovedIngreso = this.ingresoData.remove(Integer.parseInt(parameters.get(0)));
                return isRemovedIngreso ? "removed Ingreso successfully" : "I have an error to remove Ingreso";
            } else {
                return "Doesn't exists Ingreso ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to remove Ingreso";
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