package com.example.highcakes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniqueController {
    @GetMapping("/unique")
    private String mainPage(){
        return "unique";
    }
}
