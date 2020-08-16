package com.uet.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uet.moneymanager.fragment.AddTransactionFragment;
import com.uet.moneymanager.fragment.StatisticFragment;
import com.uet.moneymanager.fragment.TransactionFragment;
import com.uet.moneymanager.util.Database;

public class MainActivity extends AppCompatActivity {

    final Fragment transactionFragment = new TransactionFragment();
    final Fragment addTransactionFrament = new AddTransactionFragment();
    final Fragment statisticFragment = new StatisticFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, transactionFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()){
                        case R.id.nav_transaction:
                            fragment = new TransactionFragment();
                            loadFlagment(fragment);
                            return true;
                        case  R.id.nav_add_transaction:
                            fragment = new AddTransactionFragment();
                            loadFlagment(fragment);
                            return true;
                        case  R.id.nav_statistic:
                            fragment = new StatisticFragment();
                            loadFlagment(fragment);
                            return true;
                    }
                    return false;
                }
            };

    public void loadFlagment(Fragment fragment){
        //load flagment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}