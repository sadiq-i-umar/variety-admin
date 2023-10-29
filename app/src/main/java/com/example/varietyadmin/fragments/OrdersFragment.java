package com.example.varietyadmin.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.format.DateFormat;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private SwipeRefreshLayout swipeRefreshLayout;

    private MaterialToolbar topAppBar;

    private boolean fromUnpaidOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String previousOrderList = sharedPreferences.getString("previousOrderList", "");

        nestedSV = view.findViewById(R.id.idNestedSV);

        loadingPB = view.findViewById(R.id.idPBLoading);

        topAppBar = getActivity().findViewById(R.id.topAppBar);

        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        Call<OrdersResponse> call = RetrofitClient.getInstance().getApi().getAllOrdersWithDate();
        Call<OrdersResponse> unpaidOrdersCall = RetrofitClient.getInstance().getApi().getUnpaidOrders();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("messages");

        myRef.setValue("Hello from our Course!");

        loadingPB.setVisibility(View.VISIBLE);

        if (previousOrderList.equals("") || previousOrderList.equals("all")) {
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
        } else if (previousOrderList.equals("unpaid")) {
            unpaidOrdersCall.clone().enqueue(new Callback<OrdersResponse>() {
                @Override
                public void onResponse(Call<OrdersResponse> unpaidOrdersCall, Response<OrdersResponse> response) {
                    orderList = response.body().getOrders();
                    orderAdapter = new OrderAdapter(orderList, getActivity());
                    orderRecyclerView.setAdapter(orderAdapter);
                    loadingPB.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<OrdersResponse> unpaidOrdersCall, Throwable t) {

                }
            });
        }

        topAppBar.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                loadingPB.setVisibility(View.VISIBLE);
                call.clone().enqueue(new Callback<OrdersResponse>() {
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
                return false;
            }
        });

        //On clicking Unpaid
        topAppBar.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                unpaidOrdersCall.clone().enqueue(new Callback<OrdersResponse>() {
                    @Override
                    public void onResponse(Call<OrdersResponse> unpaidOrdersCall, Response<OrdersResponse> response) {
                        orderList = response.body().getOrders();
                        orderAdapter = new OrderAdapter(orderList, getActivity());
                        orderRecyclerView.setAdapter(orderAdapter);
                        loadingPB.setVisibility(View.GONE);
                        editor.putString("previousOrderList", "unpaid");
                        editor.commit();
                    }

                    @Override
                    public void onFailure(Call<OrdersResponse> unpaidOrdersCall, Throwable t) {

                    }
                });
                return false;
            }
        });

        //On clicking receipts
        topAppBar.getMenu().getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call.clone().enqueue(new Callback<OrdersResponse>() {
                    @Override
                    public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                        orderList = response.body().getOrders();
                        orderAdapter = new OrderAdapter(orderList, getActivity());
                        orderRecyclerView.setAdapter(orderAdapter);
                        loadingPB.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<OrdersResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("previousOrderList", "");
        editor.commit();
    }
}