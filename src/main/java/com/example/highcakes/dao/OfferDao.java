package com.example.highcakes.dao;

import com.example.highcakes.model.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferDao {
    Offer save(Offer offer);

    void delete(Long id);

    List<Offer> findAll();

    Optional<Offer> findById(Long id);

    Offer updateFields(Long id, String name, String phone, String email);
}