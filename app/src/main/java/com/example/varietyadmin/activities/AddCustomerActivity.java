package com.example.varietyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.varietyadmin.R;
import com.example.varietyadmin.fragments.CustomersFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class AddCustomerActivity extends AppCompatActivity {

//    ImageButton backButton;
    MaterialToolbar materialToolbar;

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

//        backButton = findViewById(R.id.backIcon);
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); //Return to previous activity
//            }
//        });

    }
}