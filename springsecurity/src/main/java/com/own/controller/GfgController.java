package com.own.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GfgController {

    @GetMapping("/gfg")
    public String helloGfg() {
        return "hello-gfg"; // Maps to hello-gfg.jsp
    }
}
