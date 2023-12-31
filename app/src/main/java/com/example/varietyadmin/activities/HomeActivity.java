package com.example.varietyadmin.activities;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.varietyadmin.R;
import com.example.varietyadmin.fragments.CustomersFragment;
import com.example.varietyadmin.fragments.OrdersFragment;
import com.example.varietyadmin.fragments.RecordFragment;
import com.example.varietyadmin.fragments.SettingsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    MaterialToolbar topAppbar;

    MenuItem menuItem;

    Handler h = new Handler();
    private boolean keep = true;
    private final int DELAY = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        //Keep returning false to Should Keep On Screen until ready to begin.
        splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
            @Override
            public boolean shouldKeepOnScreen() {
                return keep;
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(runner, DELAY);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("newmessage");

        myRef.setValue("Realtime");

//        Window w = getWindow();
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        topAppbar = findViewById(R.id.topAppBar);
        topAppbar.getMenu().getItem(0).setVisible(false);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Boolean state = getIntent().getBooleanExtra("fromSelectCustomer", false);
        int customer_id = getIntent().getIntExtra("cust_id", 0);
        if (state) {
            bottomNavigationView.setSelectedItemId(R.id.record);
            loadFragment(new RecordFragment());
        } else {
            loadFragment(new OrdersFragment());
        }

    }

    private final Runnable runner = new Runnable() {
        @Override
        public void run() {
            keep = false;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.orders:
                fragment = new OrdersFragment();
                topAppbar.getMenu().getItem(0).setVisible(false);
                topAppbar.getMenu().getItem(1).setVisible(true);
                topAppbar.getMenu().getItem(2).setVisible(true);
                topAppbar.getMenu().getItem(3).setVisible(true);
                topAppbar.getMenu().getItem(4).setVisible(true);
                break;
            case R.id.record:
                fragment = new RecordFragment();
                topAppbar.getMenu().getItem(0).setVisible(false);
                topAppbar.getMenu().getItem(1).setVisible(false);
                topAppbar.getMenu().getItem(2).setVisible(false);
                topAppbar.getMenu().getItem(3).setVisible(false);
                topAppbar.getMenu().getItem(4).setVisible(false);
                break;
            case R.id.customers:
                fragment = new CustomersFragment();
                topAppbar.getMenu().getItem(0).setVisible(true);
                topAppbar.getMenu().getItem(1).setVisible(false);
                topAppbar.getMenu().getItem(2).setVisible(false);
                topAppbar.getMenu().getItem(3).setVisible(false);
                topAppbar.getMenu().getItem(4).setVisible(false);
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                topAppbar.getMenu().getItem(0).setVisible(false);
                topAppbar.getMenu().getItem(1).setVisible(false);
                topAppbar.getMenu().getItem(2).setVisible(false);
                topAppbar.getMenu().getItem(3).setVisible(false);
                topAppbar.getMenu().getItem(4).setVisible(false);
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean fromOrderDetails = sharedPreferences.getBoolean("fromOrderDetails", false);
        if (fromOrderDetails) {
            editor.putBoolean("fromOrderDetails", false);
            editor.commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, new OrdersFragment()).commit();
        }
    }
}