
package org.example;

import org.example.dao.DatabaseInitializer;
import org.example.ui.Authentication;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println(" STUDENT DORMITORY AND UTILITY BILLING");
        System.out.println("==========================================");

        try {

            // SHOW DATABASE LOCATION
            File databaseFile = new File("dormitory_billing.db");

            System.out.println("\nDATABASE INFORMATION");
            System.out.println("------------------------------------------");
            System.out.println("Database Name: dormitory_billing.db");
            System.out.println("Database Location: "
                    + databaseFile.getAbsolutePath());
            System.out.println("Database Exists: "
                    + databaseFile.exists());
            System.out.println("------------------------------------------");

            // INITIALIZE DATABASE
            DatabaseInitializer.initializeDatabase();

            System.out.println("\nDatabase initialized successfully!");

            // START AUTHENTICATION
            Authentication authentication = new Authentication();

            authentication.start();

        } catch (Exception e) {

            System.out.println("\nApplication startup failed!");
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();
        }
    }
}
