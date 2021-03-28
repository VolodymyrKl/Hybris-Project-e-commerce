package com.ciklum.hybris.vkliuiko.repository;

import javax.persistence.Table;

@Table(name = "order_items")
public class OrderItems {

    private int userId;
    private int productId;
    private int quantity;

    public OrderItems(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
