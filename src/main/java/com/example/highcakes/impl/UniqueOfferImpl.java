package com.example.highcakes.impl;

import com.example.highcakes.dao.UniqueOfferDao;
import com.example.highcakes.model.UniqueOffer;
import com.example.highcakes.repo.UniqueOfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniqueOfferImpl implements UniqueOfferDao {

    private final UniqueOfferRepo uniqueOfferRepo;

    public UniqueOfferImpl(UniqueOfferRepo uniqueOfferRepo) {
        this.uniqueOfferRepo = uniqueOfferRepo;
    }

    @Override
    public UniqueOffer save(UniqueOffer uniqueOffer) {
        return uniqueOfferRepo.save(uniqueOffer);
    }

    @Override
    public void delete(Long id) {
        uniqueOfferRepo.deleteById(id);
    }

    @Override
    public List<UniqueOffer> findAll() {
        return uniqueOfferRepo.findAll();
    }

    @Override
    public Optional<UniqueOffer> findById(Long id) {
        return uniqueOfferRepo.findById(id);
    }

    @Override
    public UniqueOffer updateFields(Long id, String name, String phone, String email, String weight, String description) {
        Optional<UniqueOffer> optionalUniqueOffer = uniqueOfferRepo.findById(id);
        if (optionalUniqueOffer.isPresent()) {
            UniqueOffer existingUniqueOffer = optionalUniqueOffer.get();
            existingUniqueOffer.setName(name);
            existingUniqueOffer.setPhone(phone);
            existingUniqueOffer.setEmail(email);
            existingUniqueOffer.setWeight(weight);
            existingUniqueOffer.setDescription(description);
            return uniqueOfferRepo.save(existingUniqueOffer);
        }
        throw new IllegalArgumentException("Invalid offer Id: " + id);
    }
}
