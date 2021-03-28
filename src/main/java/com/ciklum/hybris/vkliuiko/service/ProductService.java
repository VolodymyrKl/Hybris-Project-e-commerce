package com.ciklum.hybris.vkliuiko.service;

import com.ciklum.hybris.vkliuiko.repository.enums.ProductStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProductService {

    public void createProducts(Connection connection, String name, int price, ProductStatus status) {
        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        String insertQuery = "INSERT INTO Product (name, price, status, created_at)" +
                " VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setInt(2, price);
            statement.setString(3, String.valueOf(status));
            statement.setObject(4, createdAt);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void showAllProductsById(Statement statement) {
        String queryById = "SELECT * FROM Product ORDER BY id";
        System.out.printf("%3s %15s %15s %20s %30s%n", "Id", "Name", "Price", "Status", "Date");
        System.out.printf("%s%n", "-----------------------------------------------------------------------------------------");
        try (ResultSet resultSet = statement.executeQuery(queryById)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
                String dateTime = resultSet.getString("created_at");
                System.out.printf("%3s %15s %15s %20s %30s %n", id, name, price, status, dateTime);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeProductById(Connection connection, int id) {
        String deleteById = "DELETE FROM Product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeAllProducts(Statement statement) {
        String queryById = "DELETE FROM Product";
        try {
            statement.executeUpdate(queryById);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
