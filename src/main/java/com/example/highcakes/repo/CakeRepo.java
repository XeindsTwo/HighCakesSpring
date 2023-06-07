package com.example.highcakes.repo;

import com.example.highcakes.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CakeRepo extends JpaRepository<Cake, Long> {
    List<Cake> findAllByNameContainingIgnoreCaseOrPriceContainingIgnoreCaseOrCaloriesContainingIgnoreCase(String name, String price, String calories);
}