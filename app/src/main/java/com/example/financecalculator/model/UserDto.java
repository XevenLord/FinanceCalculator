package com.example.financecalculator.model;

public class UserDto {
    private String username;
    private String password;
    private String email;
    private int birthYear;

    public UserDto(String username, String email, int birthYear) {
        this.username = username;
        this.email = email;
        this.birthYear = birthYear;
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

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
