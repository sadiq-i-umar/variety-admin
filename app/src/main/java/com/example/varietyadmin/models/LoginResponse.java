package com.example.varietyadmin.models;

public class LoginResponse {
    private Boolean error;
    private String message;

    public LoginResponse(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public Boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
