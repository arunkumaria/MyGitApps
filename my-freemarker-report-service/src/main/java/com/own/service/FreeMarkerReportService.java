package com.own.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.own.model.Invoice;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class FreeMarkerReportService {

	private final Configuration freemarkerConfig;

	public FreeMarkerReportService(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

	public String generateInvoiceHtml(Invoice invoice) throws Exception {

		Template template = freemarkerConfig.getTemplate("invoice.ftl");

		Map<String, Object> model = new HashMap<>();
		model.put("invoice", invoice);

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	}
}
