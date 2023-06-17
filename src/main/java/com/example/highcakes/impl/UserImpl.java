package com.example.highcakes.impl;

import com.example.highcakes.dao.UserDao;
import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepo.deleteById(id);
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
}
