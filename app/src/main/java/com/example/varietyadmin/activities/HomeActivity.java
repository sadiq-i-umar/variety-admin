package com.example.varietyadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.example.varietyadmin.R;
import com.example.varietyadmin.fragments.CustomersFragment;
import com.example.varietyadmin.fragments.OrdersFragment;
import com.example.varietyadmin.fragments.RecordFragment;
import com.example.varietyadmin.fragments.SettingsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    MaterialToolbar topAppbar;

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

//        Window w = getWindow();
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        topAppbar = findViewById(R.id.topAppBar);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        topAppbar.setTitle("Orders");
        loadFragment(new OrdersFragment());

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
                topAppbar.setTitle("Orders");
                break;
            case R.id.record:
                fragment = new RecordFragment();
                topAppbar.setTitle("Record");
                break;
            case R.id.customers:
                fragment = new CustomersFragment();
                topAppbar.setTitle("Customers");
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                topAppbar.setTitle("Settings");
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
}