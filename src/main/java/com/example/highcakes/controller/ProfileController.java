package com.example.highcakes.controller;

import com.example.highcakes.impl.ReviewImpl;
import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Review;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserImpl userImpl;
    private final ReviewImpl reviewImpl;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userImpl.findByUsername(username);

        if (user == null) {
            return "redirect:/login?logout";
        }

        List<Review> userReviews = reviewImpl.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("Role", Role.class);
        model.addAttribute("userReviews", userReviews);

        return "profile";
    }

    @PostMapping("/profile/updateName")
    @ResponseBody
    public ResponseEntity<User> updateName(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newName = user.getName();
            User updatedUser = userImpl.updateUserName(userId, newName);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/profile/updateMail")
    @ResponseBody
    public ResponseEntity<User> updateMail(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newMail = user.getMail();
            User updatedUser = userImpl.updateUserMail(userId, newMail);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/profile/updateNumber")
    @ResponseBody
    public ResponseEntity<User> updateNumber(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newNumber = user.getNumber();
            User updatedUser = userImpl.updateUserNumber(userId, newNumber);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/profile/updateAvatar")
    @ResponseBody
    public ResponseEntity<String> updateAvatar(@RequestParam("id") Long userId,
                                               @RequestParam("filename") MultipartFile avatarFile) {
        try {
            userImpl.updateUserAvatar(userId, avatarFile);
            return ResponseEntity.ok("Аватар успешно обновлен");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}