package com.example.highcakes.controller;

import com.example.highcakes.impl.CakeImpl;
import com.example.highcakes.model.Cake;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.repo.CakeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CakeController {
    @Value("${upload.path}")
    private String uploadPath;
    private final CakeRepo cakeRepo;
    private final CakeImpl cakeImpl;

    public CakeController(CakeRepo cakeRepo, CakeImpl cakeImpl) {
        this.cakeRepo = cakeRepo;
        this.cakeImpl = cakeImpl;
    }

    @GetMapping("/catalog")
    public String cakeMainPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<Cake> cakes;
        if (param != null && !param.isEmpty()) {
            cakes = cakeRepo.findAllByNameContainingIgnoreCaseOrPriceContainingIgnoreCaseOrCaloriesContainingIgnoreCase(param, param, param);
            model.addAttribute("param", param);
        } else {
            cakes = cakeRepo.findAll();
        }
        model.addAttribute("cakes", cakes);
        return "catalog";
    }

    @PostMapping("/save/cake")
    public String save(@ModelAttribute("cake") Cake cake,
                       @RequestParam("photo") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String filename = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + filename));
            cake.setFilename(filename);
        }
        cakeImpl.save(cake);
        System.out.print("Запрос отправлен!");
        return "redirect:/catalog";
    }

    @PostMapping("/edit/cake")
    public String edit(@ModelAttribute("cake") Cake cake,
                       @RequestParam(value = "photo", required = false) MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {
        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + filename));
            cake.setFilename(filename);
        } else {
            Optional<Cake> optionalCake = cakeRepo.findById(cake.getId());
            if (optionalCake.isPresent()) {
                Cake existingCake = optionalCake.get();
                cake.setFilename(existingCake.getFilename());
            }
        }

        cakeImpl.updateFields(cake.getId(), cake.getName(), cake.getPrice(),
                cake.getDescription(), cake.getComposition(), cake.getCalories(),
                cake.getWeight(), cake.getProtein(), cake.getFat(),
                cake.getCarbohydrates(), cake.getShelfLife(), cake.getFilename());

        redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + cake.getName());
        return "redirect:/catalog";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cake cake = cakeImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        cakeImpl.delete(cake.getId());
        redirectAttributes.addFlashAttribute("success", "Успешное удаление торта " + cake.getName());
        return "redirect:/catalog";
    }

    @GetMapping("/cake/{id}/details")
    public String cakeDetails(@PathVariable("id") Long id, Model model) {
        Cake cake = cakeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + id));
        model.addAttribute("cake", cake);
        return "cake-details";
    }
}