package com.example.varietyadmin.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.varietyadmin.R;
import com.example.varietyadmin.adapter.CustomerAdapter;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.CustomersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersFragment extends Fragment {

    private RecyclerView customerRecyclerView;
    private CustomerAdapter customerAdapter;
    private List<Customer> customerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerRecyclerView = view.findViewById(R.id.customerRecyclerView);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<CustomersResponse> call = RetrofitClient.getInstance().getApi().getAllCustomers();

        call.enqueue(new Callback<CustomersResponse>() {
            @Override
            public void onResponse(Call<CustomersResponse> call, Response<CustomersResponse> response) {
                customerList = response.body().getCustomers();
                customerAdapter = new CustomerAdapter(customerList, getActivity());
                customerRecyclerView.setAdapter(customerAdapter);
            }

            @Override
            public void onFailure(Call<CustomersResponse> call, Throwable t) {

            }
        });

    }
}