package com.own.springboot_10best.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@RestController
@RequestMapping("/files")
public class FileController {

	private final Path uploadDir;

	public FileController(@Value("${file.upload-dir}") String dir) throws IOException {
		this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
		Files.createDirectories(this.uploadDir);
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		Path target = uploadDir.resolve(Path.of(file.getOriginalFilename()).getFileName().toString());
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		return "Uploaded: " + target.getFileName();
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
		Path file = uploadDir.resolve(filename).normalize();
		Resource resource = new UrlResource(file.toUri());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
}
