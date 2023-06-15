package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    private final UserImpl userImpl;

    public ProfileController(UserImpl userImpl) {
        this.userImpl = userImpl;
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        return "profile";
    }
}
