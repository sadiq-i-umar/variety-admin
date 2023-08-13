package com.example.varietyadmin.models;

import java.util.List;

public class CustomersResponse {
    private boolean error;
    private List<Customer> customers;

    public CustomersResponse(boolean error, List<Customer> customers) {
        this.error = error;
        this.customers = customers;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
