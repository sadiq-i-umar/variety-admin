package com.example.varietyadmin.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varietyadmin.R;
import com.example.varietyadmin.activities.HomeActivity;
import com.example.varietyadmin.activities.OrderDetails;
import com.example.varietyadmin.models.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<Order> orderList;
    private Context mCtx;
    private Context homeContext;

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

        holder.customerName.setText(order.getName());
        holder.items.setText(order.getItems());
        holder.collection_method.setText(order.getCollection_method());
        holder.collection_status.setText(order.getCollection_status());
        holder.payment_status.setText(order.getPayment_status());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(order.getDate());
            String day = (String) DateFormat.format("EEEE", date);
            String monthString  = (String) DateFormat.format("MMM",  date);
            String orderDate = order.getDate();
            String orderDatePart1 = orderDate.substring(0, 5);
            String orderDatePart2 = orderDate.substring(7, 10);
            String orderDateWithMonth = orderDatePart1 + monthString + orderDatePart2;
            holder.date.setText(day.substring(0, 3) + " " + orderDateWithMonth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (order.getPayment_status().equals("PAID")) {
            holder.payment_status.setTextColor(Color.rgb(139, 195, 74));
        }

        if (order.getCollection_status().equals("PICKED UP")) {
            holder.collection_status.setTextColor(Color.rgb(139, 195, 74));
        }

        if (order.getCollection_status().equals("DELIVERED")) {
            holder.collection_status.setTextColor(Color.rgb(139, 195, 74));
        }

        holder.orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = mCtx.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("orderId", order.getId());
                editor.putString("cardItems", order.getItems());
                editor.putString("cardCollectionMethod", order.getCollection_method());
                editor.putString("cardPaymentStatus", order.getPayment_status());
                editor.putString("cardCollectionStatus", order.getCollection_status());
                editor.putString("cardDate", order.getDate());
                editor.commit();

                Intent intent = new Intent(mCtx, OrderDetails.class);

                intent.putExtra("cardCollectionMethod", order.getCollection_method());
                intent.putExtra("cardPaymentStatus", order.getPayment_status());
                intent.putExtra("cardCollectionStatus", order.getCollection_status());
                intent.putExtra("orderId", order.getId());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, items, collection_method, collection_status, payment_status, date;
        CardView orderCard;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.cardName);
            items = itemView.findViewById(R.id.cardItems);
            collection_method = itemView.findViewById(R.id.cardCollectionMethod);
            collection_status = itemView.findViewById(R.id.cardCollectionStatus);
            payment_status = itemView.findViewById(R.id.cardPaymentStatus);
            date = itemView.findViewById(R.id.cardDate);
            orderCard = itemView.findViewById(R.id.orderCard);
        }
    }
}
