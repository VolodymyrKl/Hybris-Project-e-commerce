package com.ciklum.hybris.vkliuiko.service;

import com.ciklum.hybris.vkliuiko.repository.enums.OrderStatus;
import com.ciklum.hybris.vkliuiko.repository.enums.ProductStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OrderService {

    public void createOrder(Connection connection, int userId, int orderId, int productId, OrderStatus status, int quantity) {
        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        String insertQueryToOrder = "INSERT INTO Orders (user_id, status, created_at) " +
                "VALUES (?,?,?)";
        String insertQueryToOrderItem = "INSERT INTO Order_items (order_id, product_id, quantity) " +
                "VALUES (?,?,?)";
        try (PreparedStatement stmtToOrder = connection.prepareStatement(insertQueryToOrder)) {
            stmtToOrder.setInt(1, userId);
            stmtToOrder.setString(2, status.toString());
            stmtToOrder.setObject(3, createdAt);
            stmtToOrder.executeUpdate();
            try (PreparedStatement stmtToOrderItem = connection.prepareStatement(insertQueryToOrderItem)) {
                stmtToOrderItem.setInt(1, orderId);
                stmtToOrderItem.setInt(2, productId);
                stmtToOrderItem.setInt(3, quantity);
                stmtToOrderItem.executeUpdate();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void updateOrder(Connection connection, int id, int quantity) {
        String updateQuery = "UPDATE Order_items SET quantity = ? WHERE product_id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, quantity);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void showAllOrderedProductsByQuantity(Statement statement) {
        System.out.printf("%3s %15s %10s %20s %30s %7s %n", "Id", "Name", "Price", "Status", "Date", "Quantity");
        System.out.printf("%s%n", "--------------------------------------------------------------------------------------------");
        String queryByQuantity = "SELECT * FROM Product LEFT JOIN Order_items " +
                "ON Product.id = Order_items.product_id WHERE Order_items.quantity > 0 ORDER BY Order_items.quantity DESC, Order_items.product_id";
        try (ResultSet resultSet = statement.executeQuery(queryByQuantity)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
                String dateTime = resultSet.getString("created_at");
                int quantity = resultSet.getInt("Order_items.quantity");
                System.out.printf("%3s %15s %10s %20s %30s %7s %n", id, name, price, status, dateTime, quantity);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void showAllOrdersById(Statement statement) {
        System.out.printf("%3s %25s %20s %20s %30s %n", "Id", "Products total price", "Product Name", "Products Quantity", "Date");
        System.out.printf("%s%n", "-------------------------------------------------------------------------------------------------------");
        String queryById = "SELECT * FROM Product LEFT JOIN Order_items ON Product.id = Order_items.product_id ORDER BY id ASC";
        try (ResultSet resultSet = statement.executeQuery(queryById)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int totalPrice = resultSet.getInt("price");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("Order_items.quantity");
                String createdAt = resultSet.getString("created_at");
                System.out.printf("%3s %25s %20s %20s %30s %n", id, totalPrice * quantity, name, quantity, createdAt);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
