package com.example.highcakes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsDetailsController {

    @GetMapping("/news-details")
    private String mainPage(){
        return "news-details";
    }
}