package com.example.varietyadmin.models;

import java.util.List;

public class OrdersResponse {
    private boolean error;
    private List<Order> orders;

    public OrdersResponse(boolean error, List<Order> orders) {
        this.error = error;
        this.orders = orders;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
