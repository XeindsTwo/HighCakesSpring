package com.example.highcakes.dao;

import com.example.highcakes.model.Role;
import com.example.highcakes.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user, Role role);
    void delete(Long id);
    List<User> findAll();
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User updateUserName(Long id, String name);
    User updateUserMail(Long id, String mail);
    User updateUserNumber(Long id, String number);
    void updateUserAvatar(Long id, MultipartFile file);
}