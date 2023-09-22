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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
        if (user == null){
            return "redirect:/login?logout";
        }
        model.addAttribute("user", user);
        model.addAttribute("Role", Role.class);
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String edit(@ModelAttribute("user") User user,
                       @RequestParam(value = "photo", required = false) MultipartFile file,
                       RedirectAttributes redirectAttributes) {
        try {
            userImpl.editUserWithFile(user.getId(), user.getName(), user.getMail(), user.getNumber(), file);
            redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + user.getUsername());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при редактировании: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}