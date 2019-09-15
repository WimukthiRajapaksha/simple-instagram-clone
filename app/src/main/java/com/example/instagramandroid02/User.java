package com.example.instagramandroid02;

public class User {
    private String email;
    private String password;
    private String text;

    public User(String email, String password, String text) {
        this.email = email;
        this.password = password;
        this.text = text;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getText() {
        return text;
    }
}
