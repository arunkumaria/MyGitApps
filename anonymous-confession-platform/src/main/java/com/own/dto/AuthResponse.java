package com.own.dto;

import com.own.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthResponse {
	private String token;
	private Role role;
}