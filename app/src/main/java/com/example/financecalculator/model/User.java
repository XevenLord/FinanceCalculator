package com.example.financecalculator.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int birthYear;

    // Constructors, getters, and setters

    public User(int id, String username, String password, int birthYear) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
