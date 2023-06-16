package com.example.highcakes.controller;

import com.example.highcakes.dao.UserDao;
import com.example.highcakes.impl.EmailServiceImpl;
import com.example.highcakes.impl.ReviewImpl;
import com.example.highcakes.model.Review;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepo reviewRepo;
    private final ReviewImpl reviewImpl;
    private final EmailServiceImpl emailService;

    @GetMapping("/reviews")
    public String reviewsPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Review> reviews;
        if (param != null && !param.isEmpty()) {
            reviews = reviewRepo.findByUserNameIgnoreCaseContainingAndDate(param, LocalDate.parse(param));
            model.addAttribute("param", param);
        } else {
            reviews = reviewRepo.findAll();
        }
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @PostMapping("/reviews/save")
    public String createReview(@ModelAttribute("review") Review review, Principal principal) {
        review.setDate(LocalDate.now());
        reviewImpl.save(review);
        User user = review.getUser();
        String to = user.getMail();
        String subject = "Спасибо за ваш отзыв!";
        String text = "Здравствуйте, " + user.getName() + ". Вами был оставлен отзыв на сайте HighCakes\n\n\n"
                + "Ваш отзыв принят, мы благодарим наших клиентов за оставление положительных отзывов. Мы ради нести миру добро\n\n"
                + "С уваженением, HighCakes!";
        emailService.send(to, subject, text);
        return "reviews";
    }
}
