package com.uet.moneymanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.uet.moneymanager.R;
import com.uet.moneymanager.fragment.StatisticFragment;
import com.uet.moneymanager.fragment.TransactionFragment;

public class MainActivity extends AppCompatActivity {

    final Fragment transactionFragment = new TransactionFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, transactionFragment).commit();

    }

        private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_transaction:
                                fragment = new TransactionFragment();
                                break;
                            case R.id.nav_statistic:
                                fragment = new StatisticFragment();
                                break;
                        }

                        assert fragment != null;
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                        return true;
                    }
                };

}