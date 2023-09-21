package com.example.varietyadmin.models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;

import com.example.varietyadmin.activities.OrderDetails;
import com.example.varietyadmin.activities.UpdateOrder;

public class MyClickHandler {

    Context context;
    OrderDetails orderDetails = new OrderDetails();

    public MyClickHandler(Context context) {
        this.context = context;
    }

    public void goToUpdateOrder(View view) {
        Intent intent = new Intent(context, UpdateOrder.class);
        context.startActivity(intent);
    }

    public void goToUpdateOrder(MenuItem item) {
        Intent intent = new Intent(context, UpdateOrder.class);
        context.startActivity(intent);
    }

}
