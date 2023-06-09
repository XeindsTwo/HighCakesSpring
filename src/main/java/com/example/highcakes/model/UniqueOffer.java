package com.example.highcakes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unique_offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniqueOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String weight;
    private String description;
}
