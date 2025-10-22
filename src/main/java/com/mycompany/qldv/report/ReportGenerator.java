package com.mycompany.qldv.report;

import com.mycompany.qldv.controller.ApartmentController;
import com.mycompany.qldv.model.Apartment;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private ApartmentController apartmentController = new ApartmentController();

    public void generateReport(String filePath) throws JRException {
        List<Apartment> apartments = apartmentController.getApartments();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(apartments);

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/apartmentReport.jrxml");
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
    }
}
