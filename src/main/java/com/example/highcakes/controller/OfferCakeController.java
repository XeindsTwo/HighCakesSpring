package com.example.highcakes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfferCakeController {
    @GetMapping("/offer")
    private String mainPage() {
        return "offer";
    }
}
