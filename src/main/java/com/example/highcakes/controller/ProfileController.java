package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ProfileController {
    private final UserImpl userImpl;

    public ProfileController(UserImpl userImpl) {
        this.userImpl = userImpl;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userImpl.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }
}
