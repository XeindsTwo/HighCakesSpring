package com.example.highcakes.controller;

import com.example.highcakes.impl.OfferImpl;
import com.example.highcakes.model.Cake;
import com.example.highcakes.model.Offer;
import com.example.highcakes.repo.CakeRepo;
import com.example.highcakes.repo.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OfferCakeController {

    private final CakeRepo cakeRepo;
    private final OfferImpl offerImpl;
    private final EmailService emailService;

    public OfferCakeController(CakeRepo cakeRepo, OfferImpl offerImpl, EmailService emailService) {
        this.cakeRepo = cakeRepo;
        this.offerImpl = offerImpl;
        this.emailService = emailService;
    }

    @GetMapping("/offer")
    public String offerPage(Model model) {
        List<Cake> cakes = cakeRepo.findAll();
        List<Offer> offers = offerImpl.findAll();
        model.addAttribute("cakes", cakes);
        model.addAttribute("offer", new Offer());
        model.addAttribute("offers", offers);
        return "offer";
    }

    @PostMapping("/offer/save")
    public String createOffer(@ModelAttribute("offer") Offer offer) {
        offerImpl.save(offer);
        String to = offer.getEmail();
        String subject = "Заявка на заказ торта";
        String text = "Была создана новая заявка: " + offer.toString();
        emailService.sendEmail(to, subject, text);

        System.out.print("Добавлена заявка!");
        return "redirect:/offer";
    }

    @GetMapping("/offer/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Offer offer = offerImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        offerImpl.delete(offer.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление заявки " + offer.getName());
        return "redirect:/offer";
    }
}