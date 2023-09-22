package com.example.highcakes.dao;

import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user, Role role);
    void delete(Long id);
    List<User> findAll();
    Optional<User> findById(Long id);
    User findByUsername(String username);
}