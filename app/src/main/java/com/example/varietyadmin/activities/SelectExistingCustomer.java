package com.example.varietyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.adapter.CustomerAdapter;
import com.example.varietyadmin.adapter.SelectExistingCustomerAdapter;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.CustomersResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectExistingCustomer extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    private RecyclerView customerRecyclerView;
    private SelectExistingCustomerAdapter customerAdapter;
    private List<Customer> customerList;

    private NestedScrollView nestedSV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_existing_customer);

        materialToolbar = findViewById(R.id.selectCustAppBar);

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nestedSV = findViewById(R.id.idNestedSV);

        loadingPB = findViewById(R.id.idPBLoading);

        customerRecyclerView = findViewById(R.id.customerSelectRecyclerView);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<CustomersResponse> call = RetrofitClient.getInstance().getApi().getAllCustomers();

        loadingPB.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<CustomersResponse>() {
            @Override
            public void onResponse(Call<CustomersResponse> call, Response<CustomersResponse> response) {
                customerList = response.body().getCustomers();
                customerAdapter = new SelectExistingCustomerAdapter(customerList, SelectExistingCustomer.this);
                customerRecyclerView.setAdapter(customerAdapter);
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CustomersResponse> call, Throwable t) {

            }
        });
    }
}