package com.own.dto;



import com.own.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfessionRequest {

    @NotBlank
    @Size(max = 1000)
    private String content;

    private Category category;
}