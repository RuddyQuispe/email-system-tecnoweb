package edu.bo.uagrm.ficct.inf513.business.gestion_reportes;

import edu.bo.uagrm.ficct.inf513.data.gestion_ingreso_egreso.EgresoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_ingreso_egreso.IngresoData;
import edu.bo.uagrm.ficct.inf513.data.gestion_pago_aporte.*;
import edu.bo.uagrm.ficct.inf513.data.gestion_usuario_y_actividades.SocioData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 13/9/21 11:58
 */
public class ReportsBusiness {

    private IngresoData ingresoData;
    private EgresoData egresoData;
    private AportePagoData aportePagoData;
    private AporteData aporteData;
    private SocioData socioData;
    private MultaData multaData;
    private MultaSocioData multaSocioData;
    private MultaPagoData multaPagoData;

    public ReportsBusiness() {
        this.aporteData = new AporteData();
        this.socioData = new SocioData();
        this.aportePagoData = new AportePagoData();
        this.multaData = new MultaData();
        this.multaSocioData = new MultaSocioData();
        this.multaPagoData = new MultaPagoData();
        this.ingresoData = new IngresoData();
        this.egresoData = new EgresoData();
    }

    private ArrayList<ArrayList<String>> findAllIngresosAportes() {
        try {
            ResultSet resultSetAportes = aporteData.findAll();
            int countSocio = socioData.getCountSocio();
            ArrayList<ArrayList<String>> listReportResult = new ArrayList<>();
            while (resultSetAportes.next()) {
                int idAporte = Integer.parseInt(resultSetAportes.getString("id"));
                double amountAporte = Double.parseDouble(resultSetAportes.getString("monto"));
                String descriptionAporte = resultSetAportes.getString("descripcion");
                ResultSet resultSetPagoByAporte = aportePagoData.getCountPagoByAporte(idAporte);
                int countPagoByAporte = 0;
                if (resultSetPagoByAporte.next()) {
                    countPagoByAporte = Integer.parseInt(resultSetPagoByAporte.getString("count_pagos"));
                }
                double totalPercentage = (100 / (countSocio * amountAporte)) * (amountAporte * countPagoByAporte);
                listReportResult.add(new ArrayList<>(Arrays.asList(descriptionAporte, String.valueOf(totalPercentage), totalPercentage > 50 ? "green" : "red")));
            }
            return listReportResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<ArrayList<String>>();
        }
    }

    private ArrayList<ArrayList<String>> findAllIngresosMulta() {
        try {
            ResultSet resultSetMulta = multaData.findAll();
            ArrayList<ArrayList<String>> listReportResult = new ArrayList<>();
            while (resultSetMulta.next()) {
                int idMulta = Integer.parseInt(resultSetMulta.getString("id"));
                int countSocio = multaSocioData.getCountSocioByMulta(idMulta);
                double amountMulta = Double.parseDouble(resultSetMulta.getString("monto"));
                String descriptionMulta = resultSetMulta.getString("descripcion");
                ResultSet resultSetPagoByMulta = multaPagoData.getCountPagoByMulta(idMulta);
                int countPagoByMulta = 0;
                if (resultSetPagoByMulta.next()) {
                    countPagoByMulta = Integer.parseInt(resultSetPagoByMulta.getString("count_pagos"));
                }
                double totalPercentage = 0;
                if (countSocio != 0 && amountMulta != 0)
                    totalPercentage = (100 / (countSocio * amountMulta)) * (amountMulta * countPagoByMulta);
                listReportResult.add(new ArrayList<>(Arrays.asList(descriptionMulta, String.valueOf(totalPercentage), totalPercentage > 50 ? "green" : "red")));
            }
            return listReportResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<ArrayList<String>>();
        }
    }

    public ArrayList<ArrayList<String>> findAllIngreso() {
        ArrayList<ArrayList<String>> listReportResult = new ArrayList<>();
        listReportResult.addAll(this.findAllIngresosAportes());
        listReportResult.addAll(this.findAllIngresosMulta());
        try {
            ResultSet resultSetIngreso = ingresoData.findAll();
            while (resultSetIngreso.next()) {
                String description = resultSetIngreso.getString("detalle");
                listReportResult.add(new ArrayList<>(Arrays.asList(description, "100", "green")));
            }
            return listReportResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<ArrayList<String>> findAllEgreso() {
        try {
            ResultSet resultSetEgreso = egresoData.findAll();
            ArrayList<ArrayList<String>> listReportResult = new ArrayList<>();
            while (resultSetEgreso.next()) {
                String description = resultSetEgreso.getString("detalle");
                double amount = Double.parseDouble(resultSetEgreso.getString("monto"));
                listReportResult.add(new ArrayList<>(Arrays.asList(description, String.valueOf(amount), amount > 100 ? "red" : "yellow")));
            }
            return listReportResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    public static void main(String[] args) {
//        ReportsBusiness rb = new ReportsBusiness();
//        ArrayList<ArrayList<String>> result2 = rb.findAllIngreso();
//        System.out.println(result2.toString());
//        ArrayList<ArrayList<String>> result3 = rb.findAllEgreso();
//        System.out.println(result3.toString());
//    }
}
