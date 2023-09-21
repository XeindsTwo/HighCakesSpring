package com.example.highcakes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 3, max = 70)
    private String name;
    @Min(value = 500, message = "Минимальная цена - 500")
    @Max(value = 80000, message = "Максимальная цена - 80000")
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

    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;
}