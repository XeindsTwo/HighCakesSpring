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
}