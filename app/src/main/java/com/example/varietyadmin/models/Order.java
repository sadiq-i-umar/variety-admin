package com.example.varietyadmin.models;

public class Order {
    private int id;

    private String name;
    private String items;
    private String collection_method;
    private String collection_status;
    private String payment_status;
    private String date;

    public Order(int id, String name, String items, String collection_method, String collection_status, String payment_status, String date) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.collection_method = collection_method;
        this.collection_status = collection_status;
        this.payment_status = payment_status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getCollection_method() {
        return collection_method;
    }

    public void setCollection_method(String collection_method) {
        this.collection_method = collection_method;
    }

    public String getCollection_status() {
        return collection_status;
    }

    public void setCollection_status(String collection_status) {
        this.collection_status = collection_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
