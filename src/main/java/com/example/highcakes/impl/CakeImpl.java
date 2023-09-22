package com.example.highcakes.impl;

import com.example.highcakes.dao.CakeDao;
import com.example.highcakes.model.Cake;
import com.example.highcakes.repo.CakeRepo;
import com.example.highcakes.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CakeImpl implements CakeDao {
    private final CakeRepo cakeRepo;
    private final FileStorageService fileStorageService;

    @Override
    public Cake save(Cake cake, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String newFilename = fileStorageService.storeFile(file);
            String oldFilename = cake.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            cake.setFilename(newFilename);
        }
        return cakeRepo.save(cake);
    }

    @Override
    public void delete(Long id) {
        this.cakeRepo.deleteById(id);
    }

    @Override
    public List<Cake> findAll() {
        return cakeRepo.findAll();
    }

    @Override
    public Optional<Cake> findById(Long id) {
        return cakeRepo.findById(id);
    }

    @Override
    public Cake updateFields(Long id, String name, String price, String description, String composition,
                             String calories, String weight, String protein, String fat,
                             String carbohydrates, String shelfLife, MultipartFile file) {
        Cake existingCake = cakeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id: " + id));
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
            String newFilename = fileStorageService.storeFile(file);
            String oldFilename = existingCake.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            existingCake.setFilename(newFilename);
        }

        return cakeRepo.save(existingCake);
    }

    public List<Cake> searchCakes(String param) {
        if (param != null && !param.isEmpty()) {
            return cakeRepo.findAllByNameContainingIgnoreCaseOrPriceContainingIgnoreCaseOrCaloriesContainingIgnoreCase(param, param, param);
        } else {
            return findAll();
        }
    }
}