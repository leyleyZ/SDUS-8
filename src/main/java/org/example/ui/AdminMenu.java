package org.example.ui;

import org.example.dao.ActivityLogDao;
import org.example.model.ActivityLog;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private User currentUser;
    private Scanner scanner;
    private ActivityLogDao activityLogDao;

    public AdminMenu(User currentUser, Scanner scanner) {
        this.currentUser = currentUser;
        this.scanner = scanner;
        this.activityLogDao = new ActivityLogDao();
    }

    public void start() {
        int choice;
        do {
            System.out.println("\n======== SYSTEM AUDIT LOGS ========");
            System.out.println("1. View All Activity Logs");
            System.out.println("2. Filter Logs by User ID");
            System.out.println("0. Back to Admin Menu");
            System.out.println("=================================");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayAllLogs();
                    break;
                case 2:
                    filterLogsByUser();
                    break;
                case 0:
                    System.out.println("Returning to Admin Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    public void displayAllLogs() {
        List<ActivityLog> logs = activityLogDao.getAllLogs();

        System.out.println("\n======== SYSTEM AUDIT LOGS ========");
        System.out.printf("%-6s | %-7s | %-15s | %-35s | %-20s%n", "LOG ID", "USER ID", "EVENT TYPE", "DESCRIPTION", "TIMESTAMP");
        System.out.println("-".repeat(95));

        if (logs.isEmpty()) {
            System.out.println("No activity logs found.");
            return;
        }

        for (ActivityLog log : logs) {
            System.out.printf("%-6d | %-7d | %-15s | %-35s | %-20s%n",
                    log.getId(),
                    log.getUserId(),
                    log.getActionType(),
                    log.getDescription(),
                    log.getTimestamp()
            );
        }
    }

    public void filterLogsByUser() {
        System.out.print("Enter User ID to filter (0 to cancel): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid User ID number.");
            scanner.next();
        }
        int targetUserId = scanner.nextInt();
        scanner.nextLine();

        if (targetUserId == 0) {
            return;
        }

        System.out.println("\nShowing logs for User ID: " + targetUserId);
        List<ActivityLog> logs = activityLogDao.getLogsByUserId(targetUserId);

        if (logs.isEmpty()) {
            System.out.println("\nNo activity logs found.");
            return;
        }

        System.out.println("\n======== SYSTEM AUDIT LOGS ========");
        System.out.printf("%-6s | %-7s | %-15s | %-35s | %-20s%n", "LOG ID", "USER ID", "EVENT TYPE", "DESCRIPTION", "TIMESTAMP");
        System.out.println("-".repeat(95));

        for (ActivityLog log : logs) {
            System.out.printf("%-6d | %-7d | %-15s | %-35s | %-20s%n",
                    log.getId(),
                    log.getUserId(),
                    log.getActionType(),
                    log.getDescription(),
                    log.getTimestamp()
            );
        }
    }
}