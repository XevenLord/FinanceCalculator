package com.example.financecalculator.model;

public class User {
    private int id;
    private String username;
    private int birthYear;

    // Constructors, getters, and setters

    public User(int id, String username, int birthYear) {
        this.id = id;
        this.username = username;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
