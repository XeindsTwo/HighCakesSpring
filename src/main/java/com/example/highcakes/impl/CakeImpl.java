package com.example.highcakes.impl;

import com.example.highcakes.dao.CakeDao;
import com.example.highcakes.model.Cake;
import com.example.highcakes.repo.CakeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakeImpl implements CakeDao {

    private final CakeRepo cakeRepo;

    public CakeImpl(CakeRepo cakeRepo) {
        this.cakeRepo = cakeRepo;
    }

    @Override
    public Cake save(Cake cake) {
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
                             String carbohydrates, String shelfLife, String filename) {
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
            existingCake.setFilename(filename);
            return cakeRepo.save(existingCake);
        }
        throw new IllegalArgumentException("Invalid cake Id: " + id);
    }
}