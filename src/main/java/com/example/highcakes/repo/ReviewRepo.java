package com.example.highcakes.repo;

import com.example.highcakes.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByTextIgnoreCaseContaining(String text);
}