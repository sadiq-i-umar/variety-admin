package com.example.varietyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varietyadmin.R;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<Order> orderList;
    private Context mCtx;

    public OrderAdapter(List<Order> orderList, Context mCtx) {
        this.orderList = orderList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_order, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.customerName.setText(order.getCustomer_name());
        holder.items.setText(order.getItems());
        holder.collection_method.setText(order.getCollection_method());
        holder.collection_status.setText(order.getCollection_status());
        holder.payment_status.setText(order.getPayment_status());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, items, collection_method, collection_status, payment_status;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.cardName);
            items = itemView.findViewById(R.id.cardItems);
            collection_method = itemView.findViewById(R.id.cardCollectionMethod);
            collection_status = itemView.findViewById(R.id.cardCollectionStatus);
            payment_status = itemView.findViewById(R.id.cardPaymentStatus);
        }
    }
}
