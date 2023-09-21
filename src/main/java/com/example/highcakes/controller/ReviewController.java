package com.example.highcakes.controller;

import com.example.highcakes.impl.ReviewImpl;
import com.example.highcakes.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewImpl reviewImpl;

    @GetMapping("/reviews")
    public String reviewsPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Review> reviews = reviewImpl.searchReviews(param);
        model.addAttribute("reviews", reviews);
        model.addAttribute("param", param);
        return "reviews";
    }

    @PostMapping("/reviews/save")
    public String createReview(@ModelAttribute("review") Review review, Principal principal) {
        reviewImpl.save(review, principal);
        return "redirect:/catalog";
    }

    @GetMapping("/reviews/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Review review = reviewImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
        reviewImpl.delete(review.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление отзыва от " + review.getUser().getName());
        return "redirect:/reviews";
    }
}