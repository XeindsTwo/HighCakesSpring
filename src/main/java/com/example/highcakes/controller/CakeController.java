package com.example.highcakes.controller;

import com.example.highcakes.impl.CakeImpl;
import com.example.highcakes.model.Cake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CakeController {
    private final CakeImpl cakeImpl;

    @GetMapping("/catalog")
    public String cakeMainPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Cake> cakes = cakeImpl.searchCakes(param);
        model.addAttribute("cakes", cakes);
        model.addAttribute("param", param);
        return "catalog";
    }

    @PostMapping("/save/cake")
    public String save(@ModelAttribute("cake") Cake cake,
                       @RequestParam("photo") MultipartFile file) throws IOException {
        cakeImpl.save(cake, file);
        return "redirect:/catalog";
    }

    @PostMapping("/edit/cake")
    public String edit(@ModelAttribute("cake") Cake cake,
                       @RequestParam(value = "photo", required = false) MultipartFile file,
                       RedirectAttributes redirectAttributes) {
        cakeImpl.updateFields(cake.getId(), cake.getName(), cake.getPrice(),
                cake.getDescription(), cake.getComposition(), cake.getCalories(),
                cake.getWeight(), cake.getProtein(), cake.getFat(),
                cake.getCarbohydrates(), cake.getShelfLife(), file);
        redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + cake.getName());
        return "redirect:/catalog";
    }

    @GetMapping("/cake/{id}/details")
    public String cakeDetails(@PathVariable("id") Long id, Model model) {
        Cake cake = cakeImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + id));
        model.addAttribute("cake", cake);
        return "cake-details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cake cake = cakeImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        cakeImpl.delete(cake.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление торта " + cake.getName());
        return "redirect:/catalog";
    }
}