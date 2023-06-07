package com.example.highcakes.dao;

import com.example.highcakes.model.User;

public interface UserDao {

    public User findByUsername(String username);

    void save(User user);
}
