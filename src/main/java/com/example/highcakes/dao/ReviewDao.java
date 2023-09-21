package com.example.highcakes.dao;

import com.example.highcakes.model.Review;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Review save(Review review, Principal principal);

    void delete(Long id);

    List<Review> findAll();

    Optional<Review> findById(Long id);
}