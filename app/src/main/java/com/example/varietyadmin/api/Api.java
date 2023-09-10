package com.example.varietyadmin.api;

import com.example.varietyadmin.models.CustomersResponse;
import com.example.varietyadmin.models.LoginResponse;
import com.example.varietyadmin.models.OrdersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("getallcustomers")
    Call<CustomersResponse> getAllCustomers();

    @FormUrlEncoded
    @POST("addcustomer")
    Call<LoginResponse> addcustomer(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("instagram") String instagram
    );

    @FormUrlEncoded
    @POST("createorder")
    Call<LoginResponse> createorder(
            @Field("items") String items,
            @Field("collection_method") String collection_method,
            @Field("customer_id") int customer_id
    );

    @GET("getallorders")
    Call<OrdersResponse> getAllOrders();

    @GET("getallorderswithcustname")
    Call<OrdersResponse> getAllOrdersWithCustName();
}
