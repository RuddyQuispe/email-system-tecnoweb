/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.EmpleadoData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;
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
        //try {
            boolean isCreatedEmpleado =
                this.empleadoData.create(  
                Integer.parseInt(parameters.get(1)),
                parameters.get(2),
                parameters.get(3),
                parameters.get(4),
                parameters.get(5),
                parameters.get(6),
                DateString.StringToDateSQL(parameters.get(7).trim()),
                DateString.StringToDateSQL(parameters.get(8).trim()));
           return isCreatedEmpleado ? "saved empleado successfully" : "I have an error to create empleado";
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //    return "I have an error to create socio";
        //}
    }
}
