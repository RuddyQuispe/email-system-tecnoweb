/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bo.uagrm.ficct.inf513.business.gestion_usuario_y_actividades;

import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.SocioData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.UsuarioData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.mail.internet.ParseException;

/**
 *
 * @author stephani
 */
public class SocioBusiness extends UsuarioData {
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
        //try {
            boolean isCreatedSocio =
                this.socioData.create(  
                Integer.parseInt(parameters.get(1)),
                parameters.get(2),
                parameters.get(3),
                parameters.get(4),
                parameters.get(5),
                parameters.get(6),
                DateString.StringToDateSQL(parameters.get(7).trim()),
                Integer.parseInt(parameters.get(8)),
                parameters.get(9),
                DateString.StringToDateSQL(parameters.get(10).trim()));
           return isCreatedSocio ? "saved socio successfully" : "I have an error to create socio";
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //    return "I have an error to create socio";
        //}
    }
}
