package com.example.highcakes.dao;

import com.example.highcakes.model.UniqueOffer;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UniqueOfferDao {

    UniqueOffer save(UniqueOffer uniqueOffer, Principal principal);

    void delete(Long id);

    List<UniqueOffer> findAll();

    Optional<UniqueOffer> findById(Long id);
}