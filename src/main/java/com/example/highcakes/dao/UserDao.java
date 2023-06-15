package com.example.highcakes.dao;

import com.example.highcakes.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    public User save(User user);

    public void delete(Long id);

    public List<User> findAll();

    public Optional<User> findById(Long id);

    User updateFields(Long id, String name, String mail, String number, String filename);

    public User findByUsername(String username);
}
