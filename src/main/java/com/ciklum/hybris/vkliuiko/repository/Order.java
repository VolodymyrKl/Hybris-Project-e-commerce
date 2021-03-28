package com.ciklum.hybris.vkliuiko.repository;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Order(int userId, String  status, LocalDateTime createdAt) {
        this.id = userId;
        this.status = status;
        this.createdAt = createdAt;

    }
}
