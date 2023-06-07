package com.example.highcakes.controller;

import com.example.highcakes.impl.CakeImpl;
import com.example.highcakes.model.Cake;
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
import java.util.UUID;

@Controller
public class CakeController {
    @Value("${upload.path}")
    private String uploadPath;
    private final CakeRepo cakeRepo;

    public CakeController(CakeRepo cakeRepo) {
        this.cakeRepo = cakeRepo;
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
    public String save(@RequestParam("name") String name,
                       @RequestParam("price") String price,
                       @RequestParam("weight") String weight,
                       @RequestParam("calories") String calories,
                       @RequestParam("composition") String composition,
                       @RequestParam("description") String description,
                       @RequestParam("protein") String protein,
                       @RequestParam("fats") String fat,
                       @RequestParam("filename") String filename,
                       @RequestParam("carbs") String carbohydrates,
                       @RequestParam("shelfLife") String shelfLife,
                       @RequestParam("photo") MultipartFile file) throws IOException {
        Cake cake = new Cake();
        cake.setName(name);
        cake.setPrice(price);
        cake.setWeight(weight);
        cake.setCalories(calories);
        cake.setComposition(composition);
        cake.setDescription(description);
        cake.setProtein(protein);
        cake.setFilename(filename);
        cake.setFat(fat);
        cake.setCarbohydrates(carbohydrates);
        cake.setShelfLife(shelfLife);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            cake.setFilename(resultFilename);
        }
        cakeRepo.save(cake);
        System.out.print("Запрос отправлен!");
        return "redirect:/catalog";
    }

    @PostMapping("/edit/cake")
    public String edit(@RequestParam("id") Long id,
                       @RequestParam("name") String name,
                       @RequestParam("price") String price,
                       @RequestParam("weight") String weight,
                       @RequestParam("calories") String calories,
                       @RequestParam("composition") String composition,
                       @RequestParam("description") String description,
                       @RequestParam("protein") String protein,
                       @RequestParam("fats") String fat,
                       @RequestParam("carbs") String carbohydrates,
                       @RequestParam("shelfLife") String shelfLife,
                       @RequestParam(value = "photo", required = false) MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {
        Cake existingCake = cakeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + id));
        existingCake.setName(name);
        existingCake.setPrice(price);
        existingCake.setDescription(description);
        existingCake.setComposition(composition);
        existingCake.setCalories(calories);
        existingCake.setWeight(weight);
        existingCake.setProtein(protein);
        existingCake.setFat(fat);
        existingCake.setCarbohydrates(carbohydrates);
        existingCake.setShelfLife(shelfLife);

        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            existingCake.setFilename(resultFilename);
        }

        cakeRepo.save(existingCake);
        redirectAttributes.addFlashAttribute("success", "Успешное редактирование! Торт - " + existingCake.getName());
        return "redirect:/catalog";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cake cake = cakeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + id));
        cakeRepo.delete(cake);
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