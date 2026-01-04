package com.own.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIResponseDTO {
	private String response;
	private String model;
	private long processingTimeMs;
}
