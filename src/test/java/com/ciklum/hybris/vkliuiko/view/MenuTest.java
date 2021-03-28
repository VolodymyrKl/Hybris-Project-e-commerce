package com.ciklum.hybris.vkliuiko.view;

import com.ciklum.hybris.vkliuiko.db.connection.Connector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuTest {
    Connection connection;
    Statement statement;

    Menu menu = Mockito.mock(Menu.class);

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
    public void showMenu() {
        menu.showMenu();
        Mockito.verify(menu).showMenu();
    }

    @Test
    public void createTables() {
        menu.createTables();
        Mockito.verify(menu).createTables();
    }
}