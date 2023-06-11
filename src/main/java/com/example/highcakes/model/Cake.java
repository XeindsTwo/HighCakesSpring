package com.example.highcakes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cakes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    private String description;
    private String composition;
    private String calories;
    private String weight;
    private String protein;
    private String fat;
    private String carbohydrates;
    private String shelfLife;
    private String filename;
}