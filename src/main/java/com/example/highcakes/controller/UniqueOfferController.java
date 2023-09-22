package com.example.highcakes.controller;

import com.example.highcakes.impl.UniqueOfferImpl;
import com.example.highcakes.model.UniqueOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UniqueOfferController {
    private final UniqueOfferImpl uniqueOfferImpl;

    @GetMapping("/unique")
    public String offerPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<UniqueOffer> uniques = uniqueOfferImpl.searchUniqueOffer(param, param, param);
        UniqueOffer uniqueOffer = new UniqueOffer();

        model.addAttribute("uniques", uniques);
        model.addAttribute("param", param);
        model.addAttribute("unique", uniqueOffer);
        return "unique";
    }

    @PostMapping("/unique/save")
    public String createOffer(@ModelAttribute("offer") UniqueOffer uniqueOffer, Principal principal) {
        uniqueOfferImpl.save(uniqueOffer, principal);
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