package com.example.highcakes.impl;

import com.example.highcakes.dao.ReviewDao;
import com.example.highcakes.dao.UserDao;
import com.example.highcakes.model.Review;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewImpl implements ReviewDao {
    private final ReviewRepo reviewRepo;
    private final EmailServiceImpl emailService;
    private final UserDao userDao;

    @Override
    public Review save(Review review, Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        review.setDate(LocalDate.now());
        review.setUser(user);

        Review savedReview = reviewRepo.save(review);
        emailService.sendReviewConfirmationEmail(user);
        return savedReview;
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

    public List<Review> searchReviews(String param) {
        if (param != null && !param.isEmpty()) {
            return reviewRepo.findByTextIgnoreCaseContaining(param);
        } else {
            return findAll();
        }
    }
}