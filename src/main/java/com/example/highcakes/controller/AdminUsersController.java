package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserImpl userImpl;

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<User> users = userImpl.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
