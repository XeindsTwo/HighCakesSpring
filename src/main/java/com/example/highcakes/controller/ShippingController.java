package com.example.highcakes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShippingController {
    @GetMapping("/shipping")
    private String mainPage() {
        return "shipping";
    }
}
