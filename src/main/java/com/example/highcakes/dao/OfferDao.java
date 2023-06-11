package com.example.highcakes.dao;

import com.example.highcakes.model.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferDao {
    public Offer save(Offer offer);

    public void delete(Long id);

    public List<Offer> findAll();

    public Optional<Offer> findById(Long id);

    Offer updateFields(Long id, String name, String phone, String email);
}