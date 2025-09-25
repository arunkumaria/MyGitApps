package com.own.cfl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GfgController {
    
    // Secure this one
    @GetMapping("/gfg")
    public String helloGfg() {
        return "hello-gfg";
    }
    
    // Don't secure this
    @GetMapping("/gfg/welcome")
    @ResponseBody
    public String welcomeGfg() {
        return "Welcome to GeeksforGeeks";
    }

}