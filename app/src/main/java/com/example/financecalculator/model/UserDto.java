package com.example.financecalculator.model;

public class UserDto {
    private String uid;
    private String username;
    private String email;
    private int birthYear;

    public UserDto() {
    }

    public UserDto(String uid, String username, String email, int birthYear) {
        this.username = username;
        this.email = email;
        this.birthYear = birthYear;
    }

    public UserDto(String username, String email, int birthYear) {
        this.username = username;
        this.email = email;
        this.birthYear = birthYear;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
