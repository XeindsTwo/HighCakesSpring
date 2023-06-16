package com.example.highcakes.controller;

import com.example.highcakes.impl.EmailServiceImpl;
import com.example.highcakes.impl.OfferImpl;
import com.example.highcakes.model.Cake;
import com.example.highcakes.model.Offer;
import com.example.highcakes.repo.CakeRepo;
import com.example.highcakes.repo.OfferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OfferCakeController {

    private final CakeRepo cakeRepo;
    private final OfferRepo offerRepo;
    private final OfferImpl offerImpl;
    private final EmailServiceImpl emailService;

    @GetMapping("/offer")
    public String offerPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Cake> cakes = cakeRepo.findAll();
        List<Offer> offers = offerImpl.findAll();
        if (param != null && !param.isEmpty()) {
            offers = offerRepo.findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(param, param, param);
            model.addAttribute("param", param);
        } else {
            cakes = cakeRepo.findAll();
        }
        model.addAttribute("cakes", cakes);
        model.addAttribute("offer", new Offer());
        model.addAttribute("offers", offers);
        return "offer";
    }

    @PostMapping("/offer/save")
    public String createOffer(@ModelAttribute("offer") Offer offer) {
        offerImpl.save(offer);
        String to = offer.getEmail();
        String nameCake = offer.getCake().getName();
        String subject = "Заявка на заказ - " + nameCake;
        String text = "Здравствуйте, " + offer.getName() + ". Вами была отправлена заявка на сайте HighCakes\n\n\n"
                + "Ваш заказ принят в обработку, в дальнейшем мы вам сообщим на ваш номер телефона " + offer.getPhone() + "\n\n"
                + "С уваженением, HighCakes!";
        emailService.send(to, subject, text);

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