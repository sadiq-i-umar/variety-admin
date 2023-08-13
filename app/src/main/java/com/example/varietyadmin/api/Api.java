package com.example.varietyadmin.api;

import com.example.varietyadmin.models.CustomersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("getallcustomers")
    Call<CustomersResponse> getAllCustomers();
}
