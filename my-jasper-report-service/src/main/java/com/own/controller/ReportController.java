package com.own.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Invoice;
import com.own.model.InvoiceItem;
import com.own.service.JasperReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final JasperReportService reportService;

	public ReportController(JasperReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/invoice/{orderId}")
	public ResponseEntity<byte[]> generateInvoice(@PathVariable String orderId) throws Exception {

		// Dummy data (replace with DB later)
		List<InvoiceItem> items = List.of(new InvoiceItem("Laptop", 1, 50000), new InvoiceItem("Mouse", 2, 500));

		Invoice invoice = new Invoice("INV-" + orderId, "Arun Kumar", items, 51000);

		byte[] pdf = reportService.generateInvoicePdf(invoice);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("inline", "invoice.pdf");

		return ResponseEntity.ok().headers(headers).body(pdf);
	}
}
