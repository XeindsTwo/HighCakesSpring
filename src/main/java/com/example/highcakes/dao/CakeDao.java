package com.example.highcakes.dao;

import com.example.highcakes.model.Cake;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CakeDao {

    Cake save(Cake cake, MultipartFile file);

    void delete(Long id);

    List<Cake> findAll();

    Optional<Cake> findById(Long id);

    Cake updateFields(Long id, String name, String price,
                      String description, String composition, String calories,
                      String weight, String protein, String fat,
                      String carbohydrates, String shelfLife, MultipartFile filename);
}