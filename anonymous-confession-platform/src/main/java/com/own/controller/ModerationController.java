package com.own.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.ConfessionRequest;
import com.own.dto.ConfessionResponse;
import com.own.entity.Confession;
import com.own.entity.ConfessionStatus;
import com.own.repository.ConfessionRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/confessions")
@RequiredArgsConstructor
public class ModerationController {

	private final ConfessionRepository confessionRepository;

	// 🔹 1. Submit Confession (Anonymous)
	@PostMapping
	public ConfessionResponse submitConfession(@Valid @RequestBody ConfessionRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String ip,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) throws Exception {

		String ipHash = hashValue(ip);
		String deviceHash = hashValue(userAgent);

		Confession confession = Confession.builder().id(UUID.randomUUID()).content(request.getContent())
				.category(request.getCategory()).status(ConfessionStatus.PENDING).ipHash(ipHash).deviceHash(deviceHash)
				.createdAt(LocalDateTime.now()).build();

		Confession saved = confessionRepository.save(confession);

		return mapToPublicDTO(saved);
	}

	// 🔹 2. Get Approved Confessions (Public Feed)
	@GetMapping("/approved")
	public List<ConfessionResponse> getApprovedConfessions() {
		return confessionRepository.findByStatus(ConfessionStatus.APPROVED).stream().map(this::mapToPublicDTO)
				.collect(Collectors.toList());
	}

	// 🔹 3. Moderate Confession (Internal Use Only)
	@PreAuthorize("hasRole('MODERATOR')")
	@PutMapping("/{id}/moderate")
	public String moderateConfession(@PathVariable UUID id, @RequestParam ConfessionStatus status) {
		Confession confession = confessionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Confession not found"));

		confession.setStatus(status);
		confessionRepository.save(confession);

		return "Confession updated to " + status;
	}

	// 🔐 Hashing for Abuse Prevention
	private String hashValue(String input) throws Exception {
		if (input == null)
			return null;

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			hexString.append(String.format("%02x", b));
		}
		return hexString.toString();
	}

	private ConfessionResponse mapToPublicDTO(Confession confession) {
		return ConfessionResponse.builder().id(confession.getId()).content(confession.getContent())
				.category(confession.getCategory()).createdAt(confession.getCreatedAt()).build();
	}
}