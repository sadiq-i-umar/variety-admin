package com.example.varietyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.fragments.CustomersFragment;
import com.example.varietyadmin.models.LoginResponse;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity {

//    ImageButton backButton;
    MaterialToolbar materialToolbar;
    EditText nameEditText, phoneEditText, addressEditText, instagramEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        materialToolbar = findViewById(R.id.topAppBar);

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void addCustomer(View v) {
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        instagramEditText = findViewById(R.id.instagramEditText);

        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String instagram = instagramEditText.getText().toString();

        if (name.equals("") || phone.equals("") || address.equals("") || instagram.equals("")) {
            Toast.makeText(this, "One or more fields missing", Toast.LENGTH_SHORT).show();
        } else {
            Call<LoginResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .addcustomer(name, phone, address, instagram);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if(response.code() == 201) {
                        LoginResponse dr = response.body();
                        Toast.makeText(AddCustomerActivity.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    } else if (response.code() == 422){
                        Toast.makeText(AddCustomerActivity.this, "Customer already exists", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
    }

    public void addCustomerFromRecordFragment(View v) {
        Intent intent = new Intent(this, AddCustomerActivity.class);
        startActivity(intent);
    }
}