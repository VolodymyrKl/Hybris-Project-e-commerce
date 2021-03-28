package com.ciklum.hybris.vkliuiko.security;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SecuritySession {
    private Scanner scanner;
    public static final String SUCCESS = "Successfully!";
    public Map<Integer, String> sessionPassword;
    private int user_id = 1;
    private boolean logged;

    public void setPassword() {
        scanner = new Scanner(new InputStreamReader(System.in));
        sessionPassword = new HashMap<>();
        System.out.println("Before begin, would you like set a password? - Y/N");
        String usersInput = scanner.next();
        if (usersInput.compareToIgnoreCase("Y") == 0) {
            System.out.println("Your password:");
            usersInput = scanner.next();

        }
        sessionPassword.put(user_id, usersInput);
        System.out.println(SUCCESS);
        System.out.println("Your id is " + user_id + ". Please remember that for ordering.");
        logged = true;
        user_id++;
    }

    public boolean isLogged() {
        return logged;
    }

    public int getUser_id() {
        return user_id;
    }
}
