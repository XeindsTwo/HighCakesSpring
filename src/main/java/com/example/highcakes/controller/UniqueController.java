package com.example.highcakes.controller;

import com.example.highcakes.impl.EmailServiceImpl;
import com.example.highcakes.impl.UniqueOfferImpl;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.repo.UniqueOfferRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UniqueController {
    private final UniqueOfferRepo uniqueOfferRepo;
    private final UniqueOfferImpl uniqueOfferImpl;
    private final EmailServiceImpl emailService;

    public UniqueController(UniqueOfferRepo uniqueOfferRepo, UniqueOfferImpl uniqueOfferImpl, EmailServiceImpl emailService) {
        this.uniqueOfferRepo = uniqueOfferRepo;
        this.uniqueOfferImpl = uniqueOfferImpl;
        this.emailService = emailService;
    }

    @GetMapping("/unique")
    public String offerPage(Model model) {
        List<UniqueOffer> uniques = uniqueOfferImpl.findAll();
        model.addAttribute("unique", new UniqueOffer());
        model.addAttribute("uniques", uniques);
        return "unique";
    }

    @PostMapping("/unique/save")
    public String createOffer(@ModelAttribute("offer") UniqueOffer uniqueOffer) {
        uniqueOfferRepo.save(uniqueOffer);
        String to = uniqueOffer.getEmail();
        String subject = "Заявка на индивидуальный заказ";
        String text = "Здравствуйте. Вами была отправлена заявка на сайте HighCakes\n\n\n."
                + "Ваш заказ принят в обработку, в дальнейшем мы вам сообщим на ваш номер телефона " + uniqueOffer.getPhone() + "\n\n"
                + "С уваженением, HighCakes!";
        emailService.send(to, subject, text);
        System.out.print("Добавлена индивидуальная заявка!");
        return "redirect:/unique";
    }

    @GetMapping("/unique/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UniqueOffer uniqueOffer = uniqueOfferImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        uniqueOfferImpl.delete(uniqueOffer.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление заявки от " + uniqueOffer.getName());
        return "redirect:/unique";
    }
}