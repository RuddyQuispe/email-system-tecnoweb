package edu.bo.uagrm.ficct.inf513.business.gestion_pago_aporte;

import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.AporteData;
import edu.bo.uagrm.ficct.inf513.utils.DateString;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 2021-12-18 01:45
 */
public class AporteBusiness {
    private AporteData aporteData;

    public AporteBusiness() {
        this.aporteData = new AporteData();
    }

    /**
     * create new input
     *
     * @param parameters: info data input [description, dateInitial, dateLimit, amount]
     * @return message to return email
     */
    public String createAporte(List<String> parameters) {
        if (parameters.size() != 5) return "ERROR: Datos insuficientes para crear un aporte";
        try {
            boolean isCreatedAporte =
                    this.aporteData.create(
                            parameters.get(0),
                            DateString.StringToDateSQL(parameters.get(1)),
                            DateString.StringToDateSQL(parameters.get(2)),
                            Double.parseDouble(parameters.get(3)),
                            Integer.parseInt(parameters.get(4)));
            return isCreatedAporte ? "Aporte guardado correctamente" : "ERROR: Hubo errores al guardar un aporte";
        } catch (ParseException e) {
            e.printStackTrace();
            return "ERROR: Tengo errores al crear nuevo aporte";
        }
    }

    /**
     * get list input
     *
     * @return arraylist input saved
     */
    public ArrayList<ArrayList<String>> findAll() {
        ResultSet data = this.aporteData.findAll();
        return this.getDataList(data);
    }

    /**
     * update input by id
     *
     * @param parameters: list new info [id, description, dateInitial, dateLimit, amount]
     * @return message to return
     */
    public String updateAporte(List<String> parameters) {
        try {
            if (parameters.size() != 6) return "ERROR: Datos insuficiente para modificar aporte";
            boolean isUpdatedAporte = this.aporteData.update(
                    Integer.parseInt(parameters.get(0)),
                    parameters.get(1),
                    DateString.StringToDateSQL(parameters.get(2).trim()),
                    DateString.StringToDateSQL(parameters.get(3).trim()),
                    Double.parseDouble(parameters.get(4)),
                    Integer.parseInt(parameters.get(5)));
            return isUpdatedAporte ?
                    "Aporte modificado correctamente" : "ERROR: no se pudo modificar los datos de aporte";
        } catch (ParseException e) {
            e.printStackTrace();
            return "ERROR: tuvimos problemas para modificar aporte";
        }
    }

    /**
     * removes an input by identifier
     *
     * @param parameters: id identifier
     * @return message if result
     */
    public String removeAporte(List<String> parameters) {
        if (parameters.size() != 1) return "ERROR: Datos insufientes para eliminar un aporte";
        try {
            boolean isRemovedAporte =
                    this.aporteData.remove(Integer.parseInt(parameters.get(0)));
            return isRemovedAporte ?
                    "Aporte eliminado correctamente" : "ERROR: No se elimino el aporte, intente de nuevo porfavor";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERROR: Tengo problemas al eliminar un aporte";
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

    public static void main(String[] args) {
        AporteBusiness inputBusiness = new AporteBusiness();
        ArrayList<ArrayList<String>> response = inputBusiness.findAll();
        System.out.println(response);
    }
}
