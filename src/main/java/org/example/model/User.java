package org.example.model;

public class User {
    private int id;
    private String username;
    private String password; // O unsa man ang naa nimo (e.g., role, status)
    private String role;
    private String status;

    // 1. Default (No-argument) Constructor - Gikinahanglan kini sa uban parts sa imong code
    public User() {
    }

    // 2. Parameterized Constructor - Kini ang gipangita karon sa imong UserDao
    public User(int id, String username, String password, String role, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Alternatibo kon 4 ra o lahi ang gidaghanon sa parameters sa imong UserDao,
    // pero pwede nimo i-match ani base sa error nimo (5 arguments).

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}