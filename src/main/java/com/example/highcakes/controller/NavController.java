package com.example.highcakes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
    @GetMapping("/index")
    private String mainPage(){
        return "index";
    }

    @GetMapping("/about")
    private String aboutPage(){
        return "about";
    }

    @GetMapping("/news")
    private String newsPage(){
        return "news";
    }

    @GetMapping("/news-details")
    private String newsDetailsPage(){
        return "news-details";
    }

    @GetMapping("/shipping")
    private String shippingPage() {
        return "shipping";
    }
}
