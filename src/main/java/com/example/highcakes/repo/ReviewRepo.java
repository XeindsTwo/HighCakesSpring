package com.example.highcakes.repo;

import com.example.highcakes.model.Review;
import com.example.highcakes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.user = :user")
    List<Review> findByUser(@Param("user") User user);
    List<Review> findByTextIgnoreCaseContaining(String text);
}