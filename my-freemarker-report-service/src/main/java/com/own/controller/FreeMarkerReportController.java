package com.own.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Invoice;
import com.own.model.InvoiceItem;
import com.own.service.FreeMarkerReportService;

@RestController
@RequestMapping("/api/reports")
public class FreeMarkerReportController {

	private final FreeMarkerReportService reportService;

	public FreeMarkerReportController(FreeMarkerReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping(value = "/invoice/{orderId}/html", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> generateInvoiceHtml(@PathVariable String orderId) throws Exception {

		List<InvoiceItem> items = List.of(new InvoiceItem("Laptop", 1, 50000), new InvoiceItem("Mouse", 2, 500));

		Invoice invoice = new Invoice("INV-" + orderId, "Arun Kumar", items, 51000);

		String html = reportService.generateInvoiceHtml(invoice);

		return ResponseEntity.ok(html);
	}
}
