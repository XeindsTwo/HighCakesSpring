package com.example.highcakes.impl;

import com.example.highcakes.dao.CakeDao;
import com.example.highcakes.model.Cake;
import com.example.highcakes.repo.CakeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CakeImpl implements CakeDao {
    @Value("${upload.path}")
    private String uploadPath;
    private final CakeRepo cakeRepo;

    public CakeImpl(CakeRepo cakeRepo) {
        this.cakeRepo = cakeRepo;
    }

    @Override
    public Cake save(Cake cake, MultipartFile file) {
        updateCakeFile(cake, file, uploadPath);
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
        Optional<Cake> optionalCake = cakeRepo.findById(id);
        if (optionalCake.isPresent()) {
            Cake existingCake = optionalCake.get();
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
            updateCakeFile(existingCake, file, uploadPath);

            return cakeRepo.save(existingCake);
        }
        throw new IllegalArgumentException("Invalid cake Id: " + id);
    }

    private void updateCakeFile(Cake cake, MultipartFile file, String uploadPath) {
        if (file != null && !file.isEmpty()) {
            try {
                String newFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
                String fullFilePath = uploadPath + "/" + newFilename;
                File newFile = new File(fullFilePath);

                if (cake.getFilename() != null) {
                    File oldFile = new File(uploadPath + "/" + cake.getFilename());
                    if (oldFile.exists() && oldFile.isFile()) {
                        if (!oldFile.delete()) {
                            throw new IllegalArgumentException("Ошибка удаления существующего файла");
                        }
                    }
                }

                file.transferTo(newFile);
                cake.setFilename(newFilename);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Ошибка при загрузке файла");
            }
        }
    }

    public List<Cake> searchCakes(String param) {
        if (param != null && !param.isEmpty()) {
            return cakeRepo.findAllByNameContainingIgnoreCaseOrPriceContainingIgnoreCaseOrCaloriesContainingIgnoreCase(param, param, param);
        } else {
            return findAll();
        }
    }
}