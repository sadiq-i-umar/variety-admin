package com.example.varietyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varietyadmin.R;
import com.example.varietyadmin.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private Context mCtx;

    public CustomerAdapter(List<Customer> customerList, Context mCtx) {
        this.customerList = customerList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        holder.name.setText(customer.getName());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.customerName);
        }
    }

}
