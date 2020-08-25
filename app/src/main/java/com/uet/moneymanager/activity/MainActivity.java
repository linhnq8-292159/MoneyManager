package com.uet.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.uet.moneymanager.R;
import com.uet.moneymanager.fragment.StatisticFragment;
import com.uet.moneymanager.fragment.TransactionFragment;

public class MainActivity extends AppCompatActivity {

    final Fragment transactionFragment = new TransactionFragment();
//    final Fragment addTransactionFragment = new AddTransactionFragment();
//    final Fragment statisticFragment = new StatisticFragment();
    SpaceNavigationView spaceNavigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        spaceNavigationView = findViewById(R.id.space);

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Transaction", R.drawable.ic_transaction));
        spaceNavigationView.addSpaceItem(new SpaceItem("Statistic", R.drawable.ic_statistics));

//        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
       getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, transactionFragment).commit();



        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Uri uri;
                Intent intent = new Intent(MainActivity.this,AddTransaction.class);
                startActivityForResult(intent, 1);
                //Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        fragment = new TransactionFragment();
                        loadFragment(fragment);
                        break;
                    case 1:
                        fragment = new StatisticFragment();
                        loadFragment(fragment);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                //Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment fragment;
//                    switch (item.getItemId()){
//                        case R.id.nav_transaction:
//                            fragment = new TransactionFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case  R.id.nav_add_transaction:
//                            fragment = new AddTransactionFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case  R.id.nav_statistic:
//                            fragment = new StatisticFragment();
//                            loadFragment(fragment);
//                            return true;
//                    }
//                    return false;
//                }
//            };



    public void loadFragment(Fragment fragment){
        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}