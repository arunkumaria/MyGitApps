package com.own.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.ai.dto.ApiSpecRequest;
import com.own.ai.dto.ApiSpecResponse;
import com.own.ai.service.RagService;

@RestController
@RequestMapping("/api/spec")
public class ApiSpecController {

    private final RagService ragService;

    public ApiSpecController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/generate")
    public ApiSpecResponse generate(@RequestBody ApiSpecRequest request) {
        return ragService.generateSpec(request.getDescription());
    }
}

