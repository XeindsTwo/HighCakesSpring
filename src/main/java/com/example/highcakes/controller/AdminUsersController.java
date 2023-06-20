package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserImpl userImpl;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String usersPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<User> users;
        if (param != null && !param.isEmpty()) {
            users = userRepo.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrNumberContainingIgnoreCaseOrMailContainingIgnoreCase(param, param, param, param);
            model.addAttribute("param", param);
        } else {
            users = userRepo.findAll();
        }
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/add")
    public String pageAdd(Model model) {
        model.addAttribute("roles", Role.values());
        return "add-user";
    }

    @PostMapping("/users/save")
    public String save(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "edit-user";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        userImpl.delete(user.getId());
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален " + user.getName());
        return "redirect:/users";
    }
}
