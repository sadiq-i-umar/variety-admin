package com.example.varietyadmin.models;

public class OrderUpdateResponse {

    private String items;
    private String collection_method;
    private String date;

    public OrderUpdateResponse(String items, String collection_method, String date) {
        this.items = items;
        this.collection_method = collection_method;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
