package com.example.varietyadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varietyadmin.R;
import com.example.varietyadmin.activities.HomeActivity;
import com.example.varietyadmin.activities.SelectExistingCustomer;
import com.example.varietyadmin.models.Customer;

import java.util.List;

public class SelectExistingCustomerAdapter extends RecyclerView.Adapter<SelectExistingCustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private Context mCtx;
    private LinearLayout custLinearLayout;

    public SelectExistingCustomerAdapter(List<Customer> customerList, Context mCtx) {
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
        holder.customerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, HomeActivity.class);
                intent.putExtra("cust_id", customerList.get(position).getId());
                intent.putExtra("fromSelectCustomer", true);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView customerCard;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            customerCard = itemView.findViewById(R.id.customerCard);
            name = itemView.findViewById(R.id.customerName);
        }
    }

}
