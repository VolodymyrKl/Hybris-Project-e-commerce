package com.ciklum.hybris.vkliuiko.service;

import com.ciklum.hybris.vkliuiko.db.connection.Connector;
import com.ciklum.hybris.vkliuiko.repository.enums.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

public class OrderServiceTest {
    Connection connection;
    Statement statement;

    OrderService orderService = Mockito.mock(OrderService.class);

    @Before
    public void getConnection() {
        try {
            connection = Connector.getConnection();
            statement = connection.createStatement();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void shouldCreateNewOrder() {
        orderService.createOrder(connection, 1, 2, 3, OrderStatus.IS_ORDERED, 3);
        Mockito.verify(orderService).createOrder(connection, 1, 2, 3, OrderStatus.IS_ORDERED, 3);
    }

    @Test
    public void shouldUpdateData() {
        orderService.updateOrder(connection, 1, 3);
        Mockito.verify(orderService).updateOrder(connection, 1, 3);
    }

    @Test
    public void shouldShowAllOrderedProductsByQuantity() {
        orderService.showAllOrderedProductsByQuantity(statement);
        Mockito.verify(orderService).showAllOrderedProductsByQuantity(statement);
    }

    @Test
    public void showAllOrdersById() {
        orderService.showAllOrdersById(statement);
        Mockito.verify(orderService).showAllOrdersById(statement);
    }
}