/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.UsuarioData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;
import java.util.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author stephani
 */
public class UsuarioBusiness {
    private UsuarioData usuarioData;

    public UsuarioBusiness() {
        this.usuarioData = new UsuarioData();
    }

    /**
     * create a new User
     * @param parameters list of parameters
     * @return a message
     */
    public String createEgreso(List<String> parameters) {
        if (parameters.size() != 7) return "datos de usuario incompleto";
        //try {
            boolean isCreatedUsuario =  this.usuarioData.create( 
                            Integer.parseInt(parameters.get(1)),
                            parameters.get(2),
                            parameters.get(3),
                            parameters.get(4),
                            parameters.get(5),
                            parameters.get(6), 
                            parameters.get(7));
            return isCreatedUsuario ? "usuario guardado correctamente" : "ERROR: Hubo errores al crear un usuario";
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //  return "ERROR: Tengo errores al crear un nuevo usuario";
        //}
    }
    
    
    public ArrayList<ArrayList<String>> findAll() {
        ResultSet data = this.usuarioData.findAll();
        return this.getDataList(data);
    }
    
    
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
    
    public String updateUsuario(List<String> parameters) {
        //try {
            if (parameters.size() != 8) return "Datos insuficiente para modificar usuario";
            boolean isUpdatedUsuario = this.usuarioData.update(
                    Integer.parseInt(parameters.get(0)),
                    parameters.get(1),
                    parameters.get(2),
                    parameters.get(3),
                    parameters.get(4),
                    Boolean.parseBoolean(parameters.get(5)),
                    parameters.get(6),
                    parameters.get(7));
            return isUpdatedUsuario ?
                    "usuario modificado correctamente" : "no se pudo modificar los datos de usuario";
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //    return "ERROR: tuvimos problemas para modificar usuario";
        //} 
    }
    
    public static void main(String[] args) {
        UsuarioBusiness Usuario = new UsuarioBusiness();
        ArrayList<ArrayList<String>> response = Usuario.findAll();
        System.out.println(response);
    }

}
