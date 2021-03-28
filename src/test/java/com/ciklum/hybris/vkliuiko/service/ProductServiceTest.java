package com.ciklum.hybris.vkliuiko.service;

import com.ciklum.hybris.vkliuiko.db.connection.Connector;
import com.ciklum.hybris.vkliuiko.repository.enums.ProductStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductServiceTest {
    Connection connection;
    Statement statement;

    ProductService productService = Mockito.mock(ProductService.class);

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
    public void createProducts() {
        productService.createProducts(connection, "Samsung", 324, ProductStatus.IN_STOCK);
        Mockito.verify(productService).createProducts(connection, "Samsung", 324, ProductStatus.IN_STOCK);
    }

    @Test
    public void showAllProductsById() {
        productService.showAllProductsById(statement);
        Mockito.verify(productService).showAllProductsById(statement);
    }

    @Test
    public void removeProductById() {
        productService.removeProductById(connection, 2);
        Mockito.verify(productService).removeProductById(connection, 2);
    }

    @Test
    public void removeAllProducts() {
        productService.removeAllProducts(statement);
        Mockito.verify(productService).removeAllProducts(statement);
    }
}