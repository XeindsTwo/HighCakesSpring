package com.example.highcakes.dao;

import com.example.highcakes.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    public Review save(Review review);

    public void delete(Long id);

    public List<Review> findAll();

    public Optional<Review> findById(Long id);
}
