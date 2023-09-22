package com.example.highcakes.repo;

import com.example.highcakes.model.UniqueOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Principal;
import java.util.List;

public interface UniqueOfferRepo extends JpaRepository<UniqueOffer, Long> {
    List<UniqueOffer> findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String phone, String email);
}