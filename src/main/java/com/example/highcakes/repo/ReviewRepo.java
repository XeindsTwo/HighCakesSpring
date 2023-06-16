package com.example.highcakes.repo;

import com.example.highcakes.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByUserNameIgnoreCaseContainingAndDate(String name, LocalDate date);
}
