package com.own.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import com.own.entity.Category;
import com.own.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ConfessionResponse {
    private UUID id;
    private String content;
    private Category category;
    private LocalDateTime createdAt;
}