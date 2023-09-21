package com.example.varietyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.databinding.ActivityOrderDetailsBinding;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.GetPhoneResponse;
import com.example.varietyadmin.models.MyClickHandler;
import com.example.varietyadmin.models.LoginResponse;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity {

    private MaterialToolbar orderDetailsBar;
    private Switch paymentStatusSwitch, collectionStatusSwitch;
    private TextView paymentStatusTV, collectionStatusTV, customerNameTV, customerPhoneTV, customerAddressTV, customerInstagramTV;
    private ActivityOrderDetailsBinding binding;

    private ImageButton phoneBtn, smsMessageBtn, instagramMessageBtn;

    private MyClickHandler myClickHandler;

    private Customer customer;

    private String customerPhone, customerInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);

        orderDetailsBar = binding.orderDetailsBar;
        paymentStatusSwitch = binding.paymentStatusSwtich;
        collectionStatusSwitch = binding.collectionStatusSwitch;
        paymentStatusTV = binding.paymentStatusTV;
        collectionStatusTV = binding.collectionStatusTV;

        phoneBtn = binding.phoneBtn;
        smsMessageBtn = binding.smsMessageBtn;
        instagramMessageBtn = binding.instagramMessageBtn;

        customerNameTV = binding.customerNameTV;
        customerPhoneTV = binding.customerPhoneTV;
        customerAddressTV = binding.customerAddressTV;
        customerInstagramTV = binding.customerInstagramTV;

        myClickHandler = new MyClickHandler(this);
        binding.setMyClickHandler(myClickHandler);

        orderDetailsBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.updateOption) {
                    binding.getMyClickHandler().goToUpdateOrder(item);
                }
                if (itemId == R.id.deleteOption) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetails.this);
                    builder.setTitle("Are you sure?");
                    builder.setMessage("This action is irreversible...");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            int orderid = sharedPreferences.getInt("orderId", 0);

                            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().deleteorder(orderid);

                            call.enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                                    if(!response.body().isError()) {
                                        Intent intent = new Intent(OrderDetails.this, HomeActivity.class);
                                        startActivity(intent);
                                    }

                                    Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
                return OrderDetails.super.onOptionsItemSelected(item);
            }
        });

        orderDetailsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int orderId = getIntent().getIntExtra("orderId", 0);

        Call<LoginResponse> paidStatusCall = RetrofitClient.getInstance().getApi().changestatustopaid(orderId);
        Call<LoginResponse> notPaidStatusCall = RetrofitClient.getInstance().getApi().changestatustonotpaid(orderId);

        Call<LoginResponse> pickedUpStatusCall = RetrofitClient.getInstance().getApi().changestatustopickedup(orderId);
        Call<LoginResponse> notPickedStatusCall = RetrofitClient.getInstance().getApi().changestatustonotpickedup(orderId);

        Call<LoginResponse> deliveredStatusCall = RetrofitClient.getInstance().getApi().changestatustodelivered(orderId);
        Call<LoginResponse> notDeliveredStatusCall = RetrofitClient.getInstance().getApi().changestatustonotdelivered(orderId);

        Call<GetPhoneResponse> getPhoneResponseCall = RetrofitClient.getInstance().getApi().getCustomerPhoneFromOrder(orderId);
        Call<GetPhoneResponse> getInstagramCall = RetrofitClient.getInstance().getApi().getInstagramFromOrder(orderId);

        Call<Customer> getCustomerDataFromOrder = RetrofitClient.getInstance().getApi().getCustomerDataFromOrder(orderId);

        getPhoneResponseCall.enqueue(new Callback<GetPhoneResponse>() {
            @Override
            public void onResponse(Call<GetPhoneResponse> call, Response<GetPhoneResponse> response) {
                customerPhone = response.body().getPhone();
            }

            @Override
            public void onFailure(Call<GetPhoneResponse> call, Throwable t) {

            }
        });

        getInstagramCall.enqueue(new Callback<GetPhoneResponse>() {
            @Override
            public void onResponse(Call<GetPhoneResponse> call, Response<GetPhoneResponse> response) {
                customerInstagram = response.body().getPhone();
            }

            @Override
            public void onFailure(Call<GetPhoneResponse> call, Throwable t) {

            }
        });

        getCustomerDataFromOrder.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                customerNameTV.setText(response.body().getName());
                customerPhoneTV.setText(response.body().getPhone());
                customerAddressTV.setText(response.body().getAddress());
                customerInstagramTV.setText(response.body().getInstagram());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!customerPhone.equals("None")) {
                    Intent sendIntent = new Intent(Intent.ACTION_DIAL);
                    sendIntent.setData(Uri.parse("tel:" + customerPhone));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Number not assigned", Toast.LENGTH_SHORT).show();
                }
            }
        });

        smsMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!customerPhone.equals("None")) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:" + customerPhone));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Number not assigned", Toast.LENGTH_SHORT).show();
                }
            }
        });

        instagramMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!customerInstagram.equals("None")) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("https://instagram.com/_u/" + customerInstagram));
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Instagram account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String paymentStatus = sharedPreferences.getString("cardPaymentStatus", "Error");
        String collectionStatus = sharedPreferences.getString("cardCollectionStatus", "Error");

        if (paymentStatus.equals("PAID")) {
            paymentStatusSwitch.setChecked(true);
            paymentStatusTV.setText("PAID");
            paymentStatusTV.setTextColor(Color.rgb(139, 195, 74));
        }

        if (collectionStatus.equals("PICKED UP")) {
            collectionStatusSwitch.setChecked(true);
            collectionStatusTV.setText("PICKED UP");
            collectionStatusTV.setTextColor(Color.rgb(139, 195, 74));
        }

        if (collectionStatus.equals("NOT PICKED UP")) {
            collectionStatusTV.setText("NOT PICKED UP");
        }

        if (collectionStatus.equals("DELIVERED")) {
            collectionStatusSwitch.setChecked(true);
            collectionStatusTV.setText("DELIVERED");
            collectionStatusTV.setTextColor(Color.rgb(139, 195, 74));
        }

        if (collectionStatus.equals("NOT DELIVERED")) {
            collectionStatusTV.setText("NOT DELIVERED");
        }

        paymentStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paidStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            paymentStatusTV.setText("PAID");
                            paymentStatusTV.setTextColor(Color.rgb(139, 195, 74));
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                } else {
                    notPaidStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            paymentStatusTV.setText("NOT PAID");
                            paymentStatusTV.setTextColor(Color.RED);
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        collectionStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String collectionMethod = sharedPreferences.getString("cardCollectionMethod", "Error");
                if (isChecked) {
                    if (collectionMethod.equals("Pickup")) {
                        pickedUpStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                collectionStatusTV.setText("PICKED UP");
                                collectionStatusTV.setTextColor(Color.rgb(139, 195, 74));
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                            }
                        });
                    }
                    if (collectionMethod.equals("Delivery")) {
                        deliveredStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                collectionStatusTV.setText("DELIVERED");
                                collectionStatusTV.setTextColor(Color.rgb(139, 195, 74));
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    if (collectionMethod.equals("Pickup")) {
                        notPickedStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                collectionStatusTV.setText("NOT PICKED UP");
                                collectionStatusTV.setTextColor(Color.RED);
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                            }
                        });
                    }
                    if (collectionMethod.equals("Delivery")) {
                        notDeliveredStatusCall.clone().enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                collectionStatusTV.setText("NOT DELIVERED");
                                collectionStatusTV.setTextColor(Color.RED);
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("fromOrderDetails", true);
        editor.putBoolean("isCollectionStatusChanged", false);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String collectionStatus = sharedPreferences.getString("cardCollectionStatus", "Error");
        Boolean isCollectionStatusChanged = sharedPreferences.getBoolean("isCollectionStatusChanged", false);
        String collectionMethod = sharedPreferences.getString("cardCollectionMethod", "Error");
        if (isCollectionStatusChanged) {
            collectionStatusTV.setText(collectionStatus);
            collectionStatusTV.setTextColor(Color.RED);
            collectionStatusSwitch.setChecked(false);
            editor.commit();
        }
    }
}