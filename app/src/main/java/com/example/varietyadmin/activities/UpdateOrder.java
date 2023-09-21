package com.example.varietyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.models.LoginResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOrder extends AppCompatActivity {

    private MaterialToolbar updateOrderBar;
    private EditText itemsEditText;
    private RadioGroup myRadioGroup;
    private RadioButton radioButton;
    private RadioButton pickUpRadioButton, deliveryRadioButton;

    private Button selectDateBtn, submitBtn;

    private TextView selectedDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        updateOrderBar = findViewById(R.id.updateOrderBar);
        itemsEditText = findViewById(R.id.itemsEditText);
        myRadioGroup = findViewById(R.id.radioGroup);
        pickUpRadioButton = findViewById(R.id.pickUpRadioButton);
        deliveryRadioButton = findViewById(R.id.deliveryRadioButton);
        selectDateBtn = findViewById(R.id.selectDateBtn);
        selectedDateTV = findViewById(R.id.selectedDateTV);
        submitBtn = findViewById(R.id.submitButton);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);

        int id = sharedPreferences.getInt("orderId", 0);
        String items = sharedPreferences.getString("cardItems", "Error");
        String collection_method = sharedPreferences.getString("cardCollectionMethod", "Error");
        String date = sharedPreferences.getString("cardDate", "Error");
        String collection_status = sharedPreferences.getString("cardCollectionStatus", "Error");

        itemsEditText.setText(items);
        if (!date.equals("Error")) {
            selectedDateTV.setText(date);
            selectedDateTV.setVisibility(View.VISIBLE);
        }

        if (collection_method.equals("Pickup")) {
            pickUpRadioButton.setChecked(true);
        }

        if (collection_method.equals("Delivery")) {
            deliveryRadioButton.setChecked(true);
        }

        updateOrderBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateOrder.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (dayOfMonth < 10 && monthOfYear < 9) {
                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }
                                if (dayOfMonth < 10 && monthOfYear > 8) {
                                    selectedDateTV.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }
                                if (dayOfMonth > 10 && monthOfYear < 9) {
                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                                if (dayOfMonth > 10 && monthOfYear > 8) {
                                    selectedDateTV.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                                selectedDateTV.setVisibility(View.VISIBLE);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);

                datePickerDialog.show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String initItems = items;
                String initCollectionMethod = collection_method;
                String initDate = date;
                String items = itemsEditText.getText().toString();
                int radioButtonId = myRadioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioButtonId);
                String collection_method = radioButton.getText().toString();
                String date = selectedDateTV.getText().toString();

                Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateOrderTwo(id, items, collection_method, date, initItems, initCollectionMethod, initDate);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Toast.makeText(UpdateOrder.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("cardItems", items);
                        editor.putString("cardCollectionMethod", collection_method);
                        editor.putString("cardDate", date);
                        if (collection_method.equals(initCollectionMethod)) {
                            if (collection_method.equals("Pickup")) {
                                editor.putString("cardCollectionStatus", "PICKED UP");
                            }
                            if (collection_method.equals("Delivery")) {
                                editor.putString("cardCollectionStatus", "DELIVERED");
                            }
                            editor.putBoolean("isCollectionStatusChanged", false);
                        } else {
                            if (collection_method.equals("Pickup")) {
                                editor.putString("cardCollectionStatus", "NOT PICKED UP");
                            }
                            if (collection_method.equals("Delivery")) {
                                editor.putString("cardCollectionStatus", "NOT DELIVERED");
                            }
                            editor.putBoolean("isCollectionStatusChanged", true);
                        }
                        editor.commit();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(UpdateOrder.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}