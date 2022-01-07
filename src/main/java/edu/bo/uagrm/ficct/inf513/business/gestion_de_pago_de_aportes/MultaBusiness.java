package edu.bo.uagrm.ficct.inf513.business.gestion_de_pago_de_aportes;

import edu.bo.uagrm.ficct.inf513.data.gestion_de_pago_de_aportes.MultaData;
import edu.bo.uagrm.ficct.inf513.data.gestion_de_pago_de_aportes.MultaSocioData;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.SocioData;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-25 17:55
 */
public class MultaBusiness {
    private MultaData multaData;
    private MultaSocioData multaSocioData;
    private SocioData socioData;

    public MultaBusiness() {
        this.multaData = new MultaData();
        this.multaSocioData = new MultaSocioData();
        this.socioData = new SocioData();
    }

    public String createMulta(List<String> parameters) {
        if (parameters.size() != 2) return "ERROR: Datos insuficientes para crear una multa";
        try {
            boolean isCreatedMulta =
                    this.multaData.create(
                            parameters.get(0).trim(),
                            Double.parseDouble(parameters.get(1).trim()));
            return isCreatedMulta ? "Multa guardado correctamente" : "ERROR: Hubo errores al guardar una multa";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return "ERROR: Tengo errores al crear nueva multa";
        }
    }

    /**
     * get list input
     *
     * @return arraylist input saved
     */
    public ArrayList<ArrayList<String>> findAll() {
        ResultSet data = this.multaData.findAll();
        return this.getDataList(data);
    }

    /**
     * update multa by id
     *
     * @param parameters list id with new data for multa
     * @return message to send into email
     */
    public String updateMulta(List<String> parameters) {
        try {
            if (parameters.size() != 3) return "ERROR: Datos insuficiente para modificar multa";
            boolean isUpdatedMulta = this.multaData.update(
                    Integer.parseInt(parameters.get(0).trim()),
                    parameters.get(1).trim(),
                    Double.parseDouble(parameters.get(2).trim()));
            return isUpdatedMulta ?
                    "Multa modificado correctamente" : "ERROR: no se pudo modificar los datos de multa";
        } catch (Exception e) {
            System.out.println("ERR: " + e.getMessage());
            return "ERROR: tuvimos problemas para modificar multa";
        }
    }

    /**
     * removes an multa by identifier
     *
     * @param parameters: id identifier
     * @return message if result
     */
    public String removeMulta(List<String> parameters) {
        if (parameters.size() != 1) return "ERROR: Datos insufientes para eliminar una multa";
        try {
            boolean isRemovedMulta =
                    this.multaData.remove(Integer.parseInt(parameters.get(0).trim()));
            return isRemovedMulta ?
                    "Aporte eliminado correctamente" : "ERROR: No se elimino la multa, intente de nuevo porfavor";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERROR: Tengo problemas al eliminar una multa";
        }
    }

    /**
     * get list partners by anything id multa assigned
     *
     * @param parameters [id multa identified]
     * @return list | null
     */
    public ArrayList<ArrayList<String>> getListByIdMulta(List<String> parameters) {
        if (parameters.size() != 1) return null;
        ResultSet data = this.multaSocioData.findByIdMulta(Integer.parseInt(parameters.get(0).trim()));
        return this.getDataList(data);
    }

    public String createSocioMulta(List<String> paramenters) {
        if (paramenters.size() != 2) return "ERROR: Datos insuficientes para asignar a un socio en una multa";
        try {
            ResultSet resulSet = this.socioData.findBy("nombre", paramenters.get(1).trim());
            if (resulSet.next()) {
                int ciUser = Integer.parseInt(resulSet.getString("ci"));
                boolean isCreatedSocioMulta =
                        this.multaSocioData.create(
                                ciUser,
                                Integer.parseInt(paramenters.get(0).trim()));
                return isCreatedSocioMulta ?
                        "Socio asignado a una multa correctamente" :
                        "ERROR: Hubo errores al guardar un socio a una multa";
            } else {
                return "ERROR: No existe socio " + paramenters.get(1).trim() + ". No lo tenemos registrado";
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            return "ERROR: Tengo errores al guardar socio a una  nueva multa";
        }
    }

    /**
     * remove socio multa assigne
     *
     * @param parameters
     * @return
     */
    public String removeSocioMulta(List<String> parameters) {
        if (parameters.size() != 2) return "ERROR: Datos insufientes para eliminar un socio de una multa";
        try {
            boolean isRemovedSocioMulta =
                    this.multaSocioData.removeSocioByMulta(
                            Integer.parseInt(parameters.get(1).trim()),
                            Integer.parseInt(parameters.get(0).trim()));
            return isRemovedSocioMulta ?
                    "Socio elimina de la multa correctamente" : "ERROR: No se elimino al socio de la multa, intente de nuevo porfavor";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERROR: Tengo problemas al eliminar una multa";
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
