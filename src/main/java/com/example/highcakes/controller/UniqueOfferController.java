package com.example.highcakes.controller;

import com.example.highcakes.impl.EmailServiceImpl;
import com.example.highcakes.impl.UniqueOfferImpl;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.repo.UniqueOfferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UniqueOfferController {
    private final UniqueOfferRepo uniqueOfferRepo;
    private final UniqueOfferImpl uniqueOfferImpl;

    @GetMapping("/unique")
    public String offerPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<UniqueOffer> uniques = uniqueOfferImpl.searchUniqueOffer(param, param, param);
        model.addAttribute("uniques", uniques);
        model.addAttribute("param", param);
        return "unique";
    }

    @PostMapping("/unique/save")
    public String createOffer(@ModelAttribute("offer") UniqueOffer uniqueOffer) {
        uniqueOfferRepo.save(uniqueOffer);
        return "redirect:/unique";
    }

    @GetMapping("/unique/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UniqueOffer uniqueOffer = uniqueOfferImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        uniqueOfferImpl.delete(uniqueOffer.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление индивидуального заказа " + uniqueOffer.getName());
        return "redirect:/unique";
    }
}