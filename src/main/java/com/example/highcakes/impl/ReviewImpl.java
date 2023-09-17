package com.example.highcakes.impl;

import com.example.highcakes.dao.ReviewDao;
import com.example.highcakes.model.Review;
import com.example.highcakes.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewImpl implements ReviewDao {
    private final ReviewRepo reviewRepo;
    @Override
    public Review save(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public void delete(Long id) {
        this.reviewRepo.deleteById(id);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepo.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepo.findById(id);
    }
}