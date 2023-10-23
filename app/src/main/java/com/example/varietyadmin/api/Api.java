package com.example.varietyadmin.api;

import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.CustomersResponse;
import com.example.varietyadmin.models.GetPhoneResponse;
import com.example.varietyadmin.models.LoginResponse;
import com.example.varietyadmin.models.OrdersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST("createorderwithdate")
    Call<LoginResponse> createorderwithdate(
            @Field("items") String items,
            @Field("collection_method") String collection_method,
            @Field("customer_id") int customer_id,
            @Field("date") String date
    );

    @GET("getallorders")
    Call<OrdersResponse> getAllOrders();

    @GET("getallorderswithcustname")
    Call<OrdersResponse> getAllOrdersWithCustName();

    @GET("getallorderswithdate")
    Call<OrdersResponse> getAllOrdersWithDate();

    @GET("getunpaidorders")
    Call<OrdersResponse> getUnpaidOrders();

    @PUT("changestatustopaid/{id}")
    Call<LoginResponse> changestatustopaid(@Path("id") int id);

    @PUT("changestatustonotpaid/{id}")
    Call<LoginResponse> changestatustonotpaid(@Path("id") int id);

    @PUT("changestatustopickedup/{id}")
    Call<LoginResponse> changestatustopickedup(@Path("id") int id);

    @PUT("changestatustonotpickedup/{id}")
    Call<LoginResponse> changestatustonotpickedup(@Path("id") int id);

    @PUT("changestatustodelivered/{id}")
    Call<LoginResponse> changestatustodelivered(@Path("id") int id);

    @PUT("changestatustonotdelivered/{id}")
    Call<LoginResponse> changestatustonotdelivered(@Path("id") int id);

    @FormUrlEncoded
    @PATCH("updateorder/{id}")
    Call<LoginResponse> updateOrder(
            @Path("id") int id,
            @Field("items") String items,
            @Field("collection_method") String collection_method,
            @Field("date") String date
    );

    @FormUrlEncoded
    @PATCH("updateordertwo/{id}")
    Call<LoginResponse> updateOrderTwo(
            @Path("id") int id,
            @Field("items") String items,
            @Field("collection_method") String collection_method,
            @Field("date") String date,
            @Field("init_items") String int_items,
            @Field("init_collection_method") String init_collection_method,
            @Field("init_date") String init_date
    );

    @DELETE("deleteorder/{id}")
    Call<LoginResponse> deleteorder(
            @Path("id") int id
    );

    @GET("getcustomerphonefromorder/{orderid}")
    Call<GetPhoneResponse> getCustomerPhoneFromOrder(
            @Path("orderid") int orderid
    );

    @GET("getinstagramfromorder/{orderid}")
    Call<GetPhoneResponse> getInstagramFromOrder(
            @Path("orderid") int orderid
    );

    @GET("getcustomerdatafromorder/{orderid}")
    Call<Customer> getCustomerDataFromOrder(
            @Path("orderid") int orderid
    );

}
