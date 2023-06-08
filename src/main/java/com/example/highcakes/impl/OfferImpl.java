package com.example.highcakes.impl;

import com.example.highcakes.dao.OfferDao;
import com.example.highcakes.model.Offer;
import com.example.highcakes.repo.OfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferImpl implements OfferDao {

    private final OfferRepo offerRepo;

    public OfferImpl(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }

    @Override
    public Offer save(Offer offer) {
        return offerRepo.save(offer);
    }

    @Override
    public void delete(Long id) {
        offerRepo.deleteById(id);
    }

    @Override
    public List<Offer> findAll() {
        return offerRepo.findAll();
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerRepo.findById(id);
    }

    @Override
    public Offer updateFields(Long id, String name, String phone, String email) {
        Optional<Offer> optionalOffer = offerRepo.findById(id);
        if (optionalOffer.isPresent()) {
            Offer existingOffer = optionalOffer.get();
            existingOffer.setName(name);
            existingOffer.setPhone(phone);
            existingOffer.setEmail(email);
            return offerRepo.save(existingOffer);
        }
        throw new IllegalArgumentException("Invalid offer Id: " + id);
    }
}