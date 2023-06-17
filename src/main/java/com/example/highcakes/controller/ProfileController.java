package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    @Value("${upload.path}")
    private String uploadPath;
    private final UserImpl userImpl;
    private final UserRepo userRepo;

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
                       @RequestParam(value = "photo", required = false) MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {
        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + filename));
            user.setFilename(filename);
        } else {
            Optional<User> optionalUser = userRepo.findById(user.getId());
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                user.setFilename(existingUser.getFilename());
            }
        }
        userImpl.updateFields(user.getId(), user.getName(), user.getMail(), user.getNumber(), user.getFilename());
        redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + user.getUsername());
        return "redirect:/profile";
    }
}