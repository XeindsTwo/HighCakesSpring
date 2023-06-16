package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserImpl userImpl;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userImpl.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("Role", Role.class);
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String edit(@ModelAttribute("user") User user,
                       RedirectAttributes redirectAttributes) {
        userImpl.updateFields(user.getId(), user.getName(), user.getMail(), user.getNumber());
        redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + user.getUsername());
        return "redirect:/profile";
    }
}