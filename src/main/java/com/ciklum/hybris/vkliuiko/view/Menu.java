package com.ciklum.hybris.vkliuiko.view;

import com.ciklum.hybris.vkliuiko.repository.enums.OrderStatus;
import com.ciklum.hybris.vkliuiko.security.SecuritySession;
import com.ciklum.hybris.vkliuiko.service.ProductService;
import com.ciklum.hybris.vkliuiko.db.connection.Connector;
import com.ciklum.hybris.vkliuiko.repository.enums.ProductStatus;
import com.ciklum.hybris.vkliuiko.service.OrderService;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private ProductService productService;
    private OrderService orderService;
    private Connection connection;
    private Statement statement;
    private final SecuritySession securitySession;
    private String input;

    public Menu() {
        securitySession = new SecuritySession();
        createTables();
    }

    public void showMenu() {
        scanner = new Scanner(new InputStreamReader(System.in));
        productService = new ProductService();
        orderService = new OrderService();
        checkPassword();
        try {
            String mainMenu = "Enter a number to: \n"
                    + "1. Create Product \n"
                    + "2. Create Order \n"
                    + "3. Update Order \n"
                    + "4. Show all products \n"
                    + "5. Show all ordered products \n"
                    + "6. Show all Orders \n"
                    + "7. Remove Product";
            System.out.println(mainMenu);
            int numberFromUser = scanner.nextInt();
            switch (numberFromUser) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    addNewOrder();
                    break;
                case 3:
                    updateOrder();
                    break;
                case 4:
                    productService.showAllProductsById(statement);
                    comeBackToMenuOrExit();
                    break;
                case 5:
                    orderService.showAllOrderedProductsByQuantity(statement);
                    comeBackToMenuOrExit();
                    break;
                case 6:
                    orderService.showAllOrdersById(statement);
                    comeBackToMenuOrExit();
                    break;
                case 7:
                    removeProduct();
                    break;
                default:
                    System.out.println("Your entering was wrong. Try again");
                    showMenu();
            }
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (
                    SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void checkPassword() {
        if (!securitySession.isLogged()) {
            securitySession.setPassword();
        }
    }

    private void removeProduct() {
        int numberFromUser;
        String showRemoveMenu = "Remove by: 1. Id or " +
                "2. remove all products (you need to enter your password)";
        System.out.println(showRemoveMenu);
        numberFromUser = scanner.nextInt();
        if (numberFromUser == 1) {
            System.out.println("Do you want to see all orders? - Y/N");
            String usersWill = scanner.next();
            if (usersWill.compareToIgnoreCase("Y") == 0) {
                productService.showAllProductsById(statement);
            }
            System.out.println("Enter please now id number.");
            numberFromUser = scanner.nextInt();
            productService.removeProductById(connection, numberFromUser);
        } else if (numberFromUser == 2) {
            System.out.println("You need firstly enter your password. Have you one? Y/N");
            input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                String passwordUsers = scanner.next();
                securitySession.sessionPassword.entrySet()
                        .stream().filter(
                        s -> s.getValue().equals(passwordUsers))
                        .forEachOrdered(password -> productService
                                .removeAllProducts(statement));
                System.out.println(SecuritySession.SUCCESS);
                System.exit(0);
            } else {
                System.out.println("Firstly you need one.");
                securitySession.setPassword();
            }
        } else {
            System.out.println("Your input was incorrect. Try again");
            showMenu();
        }
        comeBackToMenuOrExit();
    }

    private void updateOrder() {
        orderService.showAllOrdersById(statement);
        System.out.println("Enter please id of order to change them");
        int id = scanner.nextInt();
        System.out.println("Set please quantity of product");
        int quantity = scanner.nextInt();
        orderService.updateOrder(connection, id, quantity);
        comeBackToMenuOrExit();
    }

    private void addNewOrder() {
        int orderId = 0;
        System.out.println("Enter please your id for order");
        int userId = scanner.nextInt();
        if (securitySession.sessionPassword.containsKey(userId)) {
            boolean ordered = true;
            productService.showAllProductsById(statement);
            while (ordered) {
                orderId++;
                System.out.println("Enter please id of product for order");
                int productId = scanner.nextInt();
                System.out.println("Enter please quantity of product for order");
                int quantity = scanner.nextInt();
                orderService.createOrder(
                        connection, securitySession.getUser_id(), orderId, productId,
                        OrderStatus.IS_ORDERED, quantity);
                System.out.println("Do you want to order else products? Y/N");
                String status = scanner.next();
                ordered = status.equalsIgnoreCase("Y");
            }
        } else {
            System.out.println("Wrong input.");
        }
        comeBackToMenuOrExit();
    }

    private void addNewProduct() {
        System.out.println("Enter please name of product");
        String name = scanner.next();
        System.out.println("Enter please price of product");
        int price = scanner.nextInt();
        System.out.println("Choose please status of product: " +
                "1. Out of stock. 2. In stock. 3. Running low");
        int statusFromUser = scanner.nextInt();
        ProductStatus status;
        if (statusFromUser == 1) {
            status = ProductStatus.OUT_OF_STOCK;
        } else if (statusFromUser == 2) {
            status = ProductStatus.IN_STOCK;
        } else if (statusFromUser == 3) {
            status = ProductStatus.RUNNING_LOW;
        } else {
            status = ProductStatus.OUT_OF_STOCK;
            System.out.println("Your number is incorrect." +
                    " The status is automatic - out of stock. But you can update your product");
        }
        productService.createProducts(connection, name, price, status);
        comeBackToMenuOrExit();
    }

    private void comeBackToMenuOrExit() {
        System.out.println(SecuritySession.SUCCESS);
        System.out.println("Do you want to go back to menu or exit - Y / N");
        input = scanner.next();
        if (input.equalsIgnoreCase("Y")) {
            showMenu();
        } else {
            System.exit(0);
        }
    }

    public void createTables() {
        try {
            connection = Connector.getConnection();
            statement = connection.createStatement();
            String productTable = "CREATE TABLE IF NOT EXISTS Product "
                    + "(id INTEGER not NULL AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "price INTEGER,"
                    + "status VARCHAR(255),"
                    + "created_at VARCHAR(255),"
                    + "PRIMARY KEY (id))";
            String orderTable = "CREATE TABLE IF NOT EXISTS Orders "
                    + "(id INTEGER not NULL AUTO_INCREMENT,"
                    + "user_id INTEGER not NULL,"
                    + "status VARCHAR(255),"
                    + "created_at VARCHAR(255),"
                    + "PRIMARY KEY (id))";
            String orderItemsTable = "CREATE TABLE IF NOT EXISTS Order_items "
                    + "(order_id INTEGER NOT NULL, "
                    + "product_id INTEGER NOT NULL,"
                    + "quantity INTEGER, "
                    + "CONSTRAINT order_categ PRIMARY KEY (order_id, product_id),"
                    + "CONSTRAINT FK_orders "
                    + "FOREIGN  KEY (order_id) REFERENCES Orders (id),"
                    + "CONSTRAINT FK_product "
                    + "FOREIGN  KEY (product_id) REFERENCES Product (id))";
            statement.executeUpdate(productTable);
            statement.executeUpdate(orderTable);
            statement.executeUpdate(orderItemsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
