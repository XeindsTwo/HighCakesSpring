package com.example.highcakes.dao;

import com.example.highcakes.model.Offer;
import com.example.highcakes.model.UniqueOffer;

import java.util.List;
import java.util.Optional;

public interface UniqueOfferDao {
    public UniqueOffer save(UniqueOffer uniqueOffer);

    public void delete(Long id);

    public List<UniqueOffer> findAll();

    public Optional<UniqueOffer> findById(Long id);

    UniqueOffer updateFields(Long id, String name, String phone, String email, String weight, String description);
}
