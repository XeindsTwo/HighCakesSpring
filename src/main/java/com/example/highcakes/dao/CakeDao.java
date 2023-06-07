package com.example.highcakes.dao;

import com.example.highcakes.model.Cake;
import java.util.List;
import java.util.Optional;

public interface CakeDao {

    public Cake save(Cake cake);

    public void delete(Long id);

    public List<Cake> findAll();

    public Optional<Cake> findById(Long id);
}
