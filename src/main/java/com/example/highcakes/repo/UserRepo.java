package com.example.highcakes.repo;

import com.example.highcakes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrNumberContainingIgnoreCaseOrMailContainingIgnoreCase
            (String username, String name, String number, String mail);
}