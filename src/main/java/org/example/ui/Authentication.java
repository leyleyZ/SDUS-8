package org.example.ui;

import org.example.dao.UserDao;
import org.example.dao.ActivityLogDao;
import org.example.model.User;

import java.util.Scanner;

public class Authentication {

    private final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDao();
    private final ActivityLogDao logDao = new ActivityLogDao();

    public void start() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("       STUDENT DORMITORY SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "0" -> {
                    System.out.println("Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void login() {

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userDao.authenticate(username, password);

        if (user == null) {
            System.out.println("Invalid username or password!");
            return;
        }

        if (!user.getStatus().equalsIgnoreCase("APPROVED")) {
            System.out.println("Your account is pending approval.");
            return;
        }

        System.out.println("Login successful!");
        System.out.println("Welcome, " + user.getUsername());

        logDao.logAction(
                user.getId(),
                "USER_LOGIN",
                user.getUsername() + " logged in."
        );

        switch (user.getRole().toUpperCase()) {

            case "ADMIN" -> {
                AdminMenu adminMenu = new AdminMenu(user, scanner);
                adminMenu.start();
            }

            case "STUDENT" -> {
                System.out.println("\n========== STUDENT MENU ==========");
                System.out.println("Welcome Student!");
            }

            case "TEACHER" -> {
                System.out.println("\n========== TEACHER MENU ==========");
                System.out.println("Welcome Teacher!");
            }

            default -> System.out.println("Unknown user role!");
        }
    }

    private void register() {

        System.out.println("\n========== REGISTRATION ==========");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (password.length() < 3) {
            System.out.println("Password must contain at least 3 characters!");
            return;
        }

        try (java.sql.Connection conn =
                     org.example.dao.DatabaseConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement("""
                 INSERT INTO users (username, password, role, status)
                 VALUES (?, ?, 'STUDENT', 'PENDING')
             """)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            int userId;

            try (java.sql.Statement stmt = conn.createStatement();
                 java.sql.ResultSet rs =
                         stmt.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                userId = rs.getInt(1);
            }

            logDao.logAction(
                    userId,
                    "USER_REGISTERED",
                    "New student account registered: " + username
            );

            System.out.println("\nRegistration successful!");
            System.out.println("Role: STUDENT");
            System.out.println("Status: PENDING");
            System.out.println("Wait for admin approval.");

        } catch (java.sql.SQLException e) {

            if (e.getMessage() != null &&
                    e.getMessage().toLowerCase().contains("unique")) {
                System.out.println("Username already exists!");
            } else {
                System.out.println("Registration error: " + e.getMessage());
            }
        }
    }
}