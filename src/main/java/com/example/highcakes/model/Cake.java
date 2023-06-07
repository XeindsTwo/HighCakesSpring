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

    @NotEmpty(message = "Название не может быть пустым")
    @Size(min = 3, max = 50, message = "Название должно содержать от 3 до 50 символов")
    private String name;

    @NotEmpty(message = "Цена не может быть пустой")
    @Min(value = 500, message = "Минимальная цена - 500")
    @Max(value = 50000, message = "Максимальная цена - 50000")
    private String price;

    @NotEmpty(message = "Описание не может быть пустым")
    @Size(min = 100, message = "Минимальное количество символов в описании - 100")
    private String description;

    @NotEmpty(message = "Состав не может быть пустым")
    private String composition;

    @NotEmpty(message = "Количество калорий не может быть пустым")
    @Size(min = 2, max = 6, message = "Количество символов в количестве калорий должно быть от 2 до 6")
    private String calories;

    @NotEmpty(message = "Вес не может быть пустым")
    @Min(value = 150, message = "Минимальный вес - 150")
    @Max(value = 20000, message = "Максимальный вес - 20000")
    private String weight;

    @NotEmpty(message = "Количество белков не может быть пустым")
    @Size(min = 2, max = 6, message = "Количество символов в количестве белков должно быть от 2 до 6")
    private String protein;

    @NotEmpty(message = "Количество жиров не может быть пустым")
    @Size(min = 2, max = 6, message = "Количество символов в количестве жиров должно быть от 2 до 6")
    private String fat;

    @NotEmpty(message = "Количество углеводов не может быть пустым")
    @Size(min = 2, max = 6, message = "Количество символов в количестве углеводов должно быть от 2 до 6")
    private String carbohydrates;

    @NotEmpty(message = "Срок хранения не может быть пустым")
    @Size(min = 1, max = 3, message = "Количество символов в сроке хранения должно быть от 1 до 3")
    private String shelfLife;

    private String filename;
}