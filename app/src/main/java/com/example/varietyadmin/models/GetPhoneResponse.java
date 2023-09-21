package com.example.varietyadmin.models;

public class GetPhoneResponse {
    private String error;
    private String phone;

    public GetPhoneResponse(String error, String phone) {
        this.error = error;
        this.phone = phone;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
