package com.example.highcakes.dao;

import com.example.highcakes.model.Cake;
import java.util.List;
import java.util.Optional;

public interface CakeDao {

    public Cake save(Cake cake);

    public void delete(Long id);

    public List<Cake> findAll();

    public Optional<Cake> findById(Long id);

    Cake updateFields(Long id, String name, String price,
                      String description, String composition, String calories,
                      String weight, String protein, String fat,
                      String carbohydrates, String shelfLife, String filename);
}