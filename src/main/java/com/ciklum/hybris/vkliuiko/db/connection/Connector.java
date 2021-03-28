package com.ciklum.hybris.vkliuiko.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Connector {
    public static Connection getConnection() throws SQLException{
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("pass", "");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("local");
        String url = resourceBundle.getString("jdbc.url");
        String username = resourceBundle.getString("jdbc.username");
        String password = resourceBundle.getString("jdbc.password");
        String driver = resourceBundle.getString("jdbc.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
}
