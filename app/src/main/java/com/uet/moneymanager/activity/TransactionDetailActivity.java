package com.uet.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.database.DatabaseAccess;

public class TransactionDetailActivity extends AppCompatActivity {

    public static final int EDIT_TRANSACTION_CODE = 1;

    TextView btnBack, btnEditTransaction;
    TextView tvTransDetailMoney, tvTransDetailNote, tvTransDetailDate, tvTransDetailGroup;
    DatabaseAccess databaseAccess;
    ImageView ivTransDetailGroupLogo;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
    }

}