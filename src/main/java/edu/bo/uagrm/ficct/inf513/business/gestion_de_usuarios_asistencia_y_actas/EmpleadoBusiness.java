/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_de_usuarios_asistencia_y_actas;

import edu.bo.uagrm.ficct.inf513.data.gestion_de_usuarios_asistencia_y_actas.EmpleadoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stephani
 */
public class EmpleadoBusiness {
     private EmpleadoData empleadoData;
    

    public EmpleadoBusiness() {
        this.empleadoData = new EmpleadoData();
         
    }

    /**
     * create a new empleado
     * @param parameters list of parameters
     * @return a message
     */

    public String create(List<String> parameters) throws java.text.ParseException {
        if (parameters.size() != 8) return "datos de empleado incompletos";
        try {
            boolean isCreatedEmpleado =
                this.empleadoData.create(  
                Integer.parseInt(parameters.get(0)),
                parameters.get(1),
                parameters.get(2),
                parameters.get(3),
                parameters.get(4),
                parameters.get(5),
                DateString.StringToDateSQL(parameters.get(6).trim()),
                DateString.StringToDateSQL(parameters.get(7).trim()));
           return isCreatedEmpleado ? "saved empleado successfully" : "I have an error to create empleado";
        } catch (ParseException e) {
            e.printStackTrace();
            return "I have an error to create socio";
        }
    }
    
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.empleadoData.findAll();
        return this.getDataList(data);
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

    /**
     * update
     * @param parameters list of parameters
     * @return a message
     *  
     */
    public String updateEmpleado(List<String> parameters) {
        try {
            if (parameters.size() != 8) return "ERROR: Datos insuficiente para modificar user empleado";
            boolean isUpdatedEmpleado= this.empleadoData.update(
                    Integer.parseInt(parameters.get(0)),  
                    parameters.get(1),
                    parameters.get(2), 
                    parameters.get(3),
                    Boolean.valueOf(parameters.get(4)),
                    parameters.get(5),
                    parameters.get(6),
                    DateString.StringToDateSQL(parameters.get(7).trim()));
            return isUpdatedEmpleado ?
                    "modificado correctamente" : "ERROR: no se pudo modificar los datos";
        } catch (ParseException e) {
            e.printStackTrace();
            return "ERROR: tuvimos problemas para modificar";
        }
    }
}
