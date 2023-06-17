package com.example.highcakes.controller;

import com.example.highcakes.dao.UserDao;
import com.example.highcakes.impl.EmailServiceImpl;
import com.example.highcakes.impl.ReviewImpl;
import com.example.highcakes.model.Review;
import com.example.highcakes.model.User;
import com.example.highcakes.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepo reviewRepo;
    private final ReviewImpl reviewImpl;
    private final UserDao userDao;
    private final EmailServiceImpl emailService;

    @GetMapping("/reviews")
    public String reviewsPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Review> reviews;
        if (param != null && !param.isEmpty()) {
            reviews = reviewRepo.findByTextIgnoreCaseContaining(param);
            model.addAttribute("param", param);
        } else {
            reviews = reviewRepo.findAll();
        }
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @PostMapping("/reviews/save")
    public String createReview(@ModelAttribute("review") Review review, Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        review.setDate(LocalDate.now());
        review.setUser(user);
        reviewImpl.save(review);

        String to = user.getMail();
        String subject = "Спасибо за ваш отзыв!";
        String text = "Здравствуйте, " + user.getName() + ". Вами был оставлен отзыв на сайте HighCakes\n\n\n"
                + "Ваш отзыв принят, мы благодарим наших клиентов за оставление положительных отзывов. Мы ради нести миру добро\n\n"
                + "С уваженением, HighCakes!";
        emailService.send(to, subject, text);
        return "redirect:/reviews";
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