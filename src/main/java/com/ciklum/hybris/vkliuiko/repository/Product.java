package com.ciklum.hybris.vkliuiko.repository;

import com.ciklum.hybris.vkliuiko.repository.enums.ProductStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private LocalDateTime dateTime;

    public Product(String name, int price, ProductStatus status, LocalDateTime dateTime) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.dateTime = dateTime;
    }
}
