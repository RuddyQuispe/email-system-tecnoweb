/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades;
 
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.AsistenciaData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.AsistenciaSocioData;
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
public class AsistenciaBusiness {
    private AsistenciaData asistenciaData;
    private AsistenciaSocioData asistenciaSocioData;

    public AsistenciaBusiness() {
        this.asistenciaData = new AsistenciaData();
        this.asistenciaSocioData = new AsistenciaSocioData();
    }

    /**
     * create a new asistencia
     * @param parameters list of parameters
     * @return a message
     */
    public String createAsistencia(List<String> parameters) {
        if (parameters.size() != 2) return "datos incompletos de asistencia";
        try { 
            boolean isCreatedAsistencia = 
                    this.asistenciaData.create( 
                            DateString.StringToDateSQL(parameters.get(0)), 
                            parameters.get(1));
            return isCreatedAsistencia ? "saved successfully" : "I have an error to create";
        } catch (ParseException e) {
            e.printStackTrace();
            return "I have an error to create";
        }
    }

    /**
     * get all data
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAll(){
        ResultSet data = this.asistenciaData.findAll();
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
     */
    public String updateAsistencia(List<String> parameters) {
        try {
            if (parameters.size() != 3) return "ERROR: Datos insuficiente para modificar asistencia";
            boolean isUpdatedAsistencia= this.asistenciaData.update(
                    Integer.parseInt(parameters.get(0)), 
                    DateString.StringToDateSQL(parameters.get(1).trim()), 
                    parameters.get(2));
            return isUpdatedAsistencia ?
                    "modificado correctamente" : "ERROR: no se pudo modificar los datos";
        } catch (ParseException e) {
            e.printStackTrace();
            return "ERROR: tuvimos problemas para modificar";
        }
    }

    /**
     * removes
     *
     * @param parameters
     * @return message if result
     */
    public String removeAsistencia(List<String> parameters) {
        if (parameters.size() != 1) return "ERROR: Datos insufientes para eliminar ";
        try {
            boolean isRemovedAsistencia =
                    this.asistenciaData.remove(Integer.parseInt(parameters.get(0)));
            return isRemovedAsistencia ?
                    "eliminado correctamente" : "ERROR: No se elimino el acta, intente nuevamente";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERROR: Tengo problemas al eliminar";
        }
    }
    
    public String createAsistenciaSocio(List<String> parameters) {
        if (parameters.size() != 2) return "datos de asistencia de socio incompleto";
        boolean isCreated = this.asistenciaSocioData.createAsistenciaSocio(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
        return isCreated ? "saved successfully" : "I have an error to create";
    }
    /**
     * get all data
     * @param  parameters list of parameters
     * @return a list of data(the first list have the attributes names)
     */
    public ArrayList<ArrayList<String>> findAllAsistenciaSocio(List<String> parameters){
        if (parameters.size() != 1) return new ArrayList();
        ResultSet data = this.asistenciaSocioData.findAllByAsistencia(Integer.parseInt(parameters.get(0)));
        return this.getDataList(data);
    }
    /**
     * delete a specific aporte pago
     * @param parameters list of parameters
     * @return a message
     */
    public String removeAsistenciaSocio(List<String> parameters) {
        if (parameters.size() != 2) return "datos de una asistencia d eun socio incompleto";
        boolean isRemoved = this.asistenciaSocioData.removeAsistenciaSocio(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
        return isRemoved ? "removed successfully" : "I have an error to remove";
    }
}
