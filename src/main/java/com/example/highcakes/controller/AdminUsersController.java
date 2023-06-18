package com.example.highcakes.controller;

import com.example.highcakes.impl.UserImpl;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserImpl userImpl;
    private final UserRepo userRepo;

    //Возможно нужно использовать HashSet для сохранения ролей при создании

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

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        userImpl.delete(user.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление пользователя " + user.getName());
        return "redirect:/users";
    }

    @GetMapping("users/save")
    public String save(Model model) {
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("roles", roles);
        return "add-user";
    }

    @GetMapping("users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + id));
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "edit-user";
    }
}
