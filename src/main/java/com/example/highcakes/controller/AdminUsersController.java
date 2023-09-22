package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserImpl userImpl;
    private final UserRepo userRepo;

    @GetMapping("/users")
    public String usersPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<User> users = userImpl.searchUser(param, param, param, param);
        model.addAttribute("users", users);
        model.addAttribute("param", param);
        return "users";
    }

    @GetMapping("/users/add")
    public String pageAdd(Model model) {
        model.addAttribute("roles", Role.values());
        return "add-user";
    }

    @PostMapping("/users/save")
    public String save(@ModelAttribute("user") User user) {
        userImpl.save(user);
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
        userImpl.delete(id);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        return "redirect:/users";
    }
}