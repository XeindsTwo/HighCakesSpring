package com.example.highcakes.controller;

import com.example.highcakes.impl.OfferImpl;
import com.example.highcakes.model.Cake;
import com.example.highcakes.model.Offer;
import com.example.highcakes.repo.CakeRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OfferCakeController {

    private final CakeRepo cakeRepo;
    private final OfferImpl offerImpl;

    public OfferCakeController(CakeRepo cakeRepo, OfferImpl offerImpl) {
        this.cakeRepo = cakeRepo;
        this.offerImpl = offerImpl;
    }

    @GetMapping("/offer")
    public String offerPage(Model model) {
        List<Cake> cakes = cakeRepo.findAll();
        List<Offer> offers = offerImpl.findAll();
        model.addAttribute("cakes", cakes);
        model.addAttribute("offers", offers);
        return "offer";
    }

    @PostMapping("/offer/save")
    public String createOffer(@ModelAttribute("offer") Offer offer) {
        offerImpl.save(offer);
        System.out.print("Добавлена заявка!");
        return "redirect:/offer";
    }
}
