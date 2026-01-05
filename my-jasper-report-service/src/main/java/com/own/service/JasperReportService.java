package com.own.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.own.model.Invoice;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportService {

    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {

        // Load JRXML
        InputStream reportStream =
                new ClassPathResource("reports/invoice.jrxml").getInputStream();

        JasperReport jasperReport =
                JasperCompileManager.compileReport(reportStream);

        // Parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoiceNumber", invoice.getInvoiceNumber());
        parameters.put("customerName", invoice.getCustomerName());
        parameters.put("totalAmount", invoice.getTotalAmount());

        // Data Source
        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(invoice.getItems());

        // Fill report
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
