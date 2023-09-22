package com.example.highcakes.impl;

import com.example.highcakes.dao.UserDao;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import com.example.highcakes.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Override
    public User save(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        userRepo.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User updateFields(Long id, String name, String mail, String number, String filename) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(name);
            existingUser.setMail(mail);
            existingUser.setNumber(number);
            existingUser.setFilename(filename);
            return userRepo.save(existingUser);
        }
        throw new IllegalArgumentException("Invalid user Id: " + id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void registerAuthUser(User user, RedirectAttributes redirectAttributes) {
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован!");
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        redirectAttributes.addFlashAttribute("success", "Вы успешно зарегистрировались!");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    public User editUserWithFile(Long id, String name, String mail, String number, MultipartFile file) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        user.setName(name);
        user.setMail(mail);
        user.setNumber(number);

        if (file != null && !file.isEmpty()) {
            String filename = fileStorageService.storeFile(file);
            String oldFilename = user.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            user.setFilename(filename);
        }
        return userRepo.save(user);
    }

    public List<User> searchUser(String param, String paramTwo, String paramThree, String paramFour) {
        if (param != null && !param.isEmpty()) {
            return userRepo.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrNumberContainingIgnoreCaseOrMailContainingIgnoreCase(param, paramTwo, paramThree, paramFour);
        } else {
            return findAll();
        }
    }
}