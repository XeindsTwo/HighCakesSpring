package com.example.highcakes.dao;

import com.example.highcakes.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user);

    void delete(Long id);

    List<User> findAll();

    Optional<User> findById(Long id);

    User updateFields(Long id, String name, String mail, String number, String filename);

    User findByUsername(String username);
}