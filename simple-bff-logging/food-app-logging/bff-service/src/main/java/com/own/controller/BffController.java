package com.own.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffController {

    private static final Logger log =
            LoggerFactory.getLogger(BffController.class);

    @GetMapping("/bff")
    public String bff() {

        log.info("BFF Service Invoked");

        return "BFF Service Running";
    }
}