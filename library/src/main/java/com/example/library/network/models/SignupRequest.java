package com.example.library.network.models;

public class SignupRequest {
    private final String email;
    private final String password;

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}