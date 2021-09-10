package com.paymybuddy.PayMyBuddy.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
public class ViewController {
    @GetMapping("/viewHome")
    public String home() {
        
        return "home";
    }
    @GetMapping("/viewIndex")
    public String index() {
        
        return "index";
    }
}
