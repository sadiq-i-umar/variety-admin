package com.example.varietyadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.activities.AddCustomerActivity;
import com.example.varietyadmin.adapter.CustomerAdapter;
import com.example.varietyadmin.adapter.OrderAdapter;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.CustomersResponse;
import com.example.varietyadmin.models.Order;
import com.example.varietyadmin.models.OrdersResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    int count = 0;
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    private NestedScrollView nestedSV;
    private ProgressBar loadingPB;

    private MaterialToolbar topAppBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nestedSV = view.findViewById(R.id.idNestedSV);

        loadingPB = view.findViewById(R.id.idPBLoading);

        topAppBar = getActivity().findViewById(R.id.topAppBar);

        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<OrdersResponse> call = RetrofitClient.getInstance().getApi().getAllOrdersWithCustName();

        loadingPB.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                orderList = response.body().getOrders();
                orderAdapter = new OrderAdapter(orderList, getActivity());
                orderRecyclerView.setAdapter(orderAdapter);
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {

            }
        });

    }

}