package com.own.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

	// 1️⃣ Upload & Read Excel
	@PostMapping("/upload")
	public List<List<String>> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
		List<List<String>> data = new ArrayList<>();

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				List<String> rowData = new ArrayList<>();
				for (Cell cell : row) {
					rowData.add(cell.toString());
				}
				data.add(rowData);
			}
		}

		return data;
	}

	// 2️⃣ Download / Generate Excel
	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadExcel() throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Sample Data");

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("ID");
			header.createCell(1).setCellValue("Name");

			Row row1 = sheet.createRow(1);
			row1.createCell(0).setCellValue(1);
			row1.createCell(1).setCellValue("John");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=sample.xlsx");

			return ResponseEntity.ok().headers(headers)
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(bos.toByteArray());
		}
	}
}
