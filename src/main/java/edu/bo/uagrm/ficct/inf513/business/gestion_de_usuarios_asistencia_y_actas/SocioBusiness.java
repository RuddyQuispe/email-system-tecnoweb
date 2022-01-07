/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_de_usuarios_asistencia_y_actas;

import edu.bo.uagrm.ficct.inf513.data.gestion_de_usuarios_asistencia_y_actas.SocioData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author stephani
 */
public class SocioBusiness  {
    private SocioData socioData;
    

    public SocioBusiness() {
        this.socioData = new SocioData();
         
    }

    /**
     * create a new socio
     * @param parameters list of parameters
     * @return a message
     */

    public String create(List<String> parameters) throws java.text.ParseException {
        if (parameters.size() != 10) return "datos de socios incompletos";
        boolean isCreatedSocio =
                this.socioData.create(
                        Integer.parseInt(parameters.get(0)),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        parameters.get(4),
                        parameters.get(5),
                        DateString.StringToDateSQL(parameters.get(6).trim()),
                        Integer.parseInt(parameters.get(7)),
                        parameters.get(8),
                        DateString.StringToDateSQL(parameters.get(9).trim()));
        return isCreatedSocio ? "saved socio successfully" : "I have an error to create socio";
    }
    
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.socioData.findAll();
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
     *  int ci, String nombre, String telefono, String email, String contrasenia, boolean estado, String direccion, int nroPuesto
     */
    public String updateSocio(List<String> parameters) {
        if (parameters.size() != 8) return "ERROR: Datos insuficiente para modificar user socio";
        boolean isUpdatedSocio= this.socioData.update(
                Integer.parseInt(parameters.get(0)),
                parameters.get(1),
                parameters.get(2),
                parameters.get(3),
                Boolean.valueOf(parameters.get(4)),
                parameters.get(5),
                parameters.get(6),
                Integer.parseInt(parameters.get(7)));
        return isUpdatedSocio ?
                "modificado correctamente" : "ERROR: no se pudo modificar los datos";
    }
}
