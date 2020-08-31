package com.uet.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.adapter.SelectTransactionGroupListViewAdapter;
import com.uet.moneymanager.database.DatabaseAccess;
import com.uet.moneymanager.model.TransactionGroup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectTransactionGroup extends AppCompatActivity {
    
    RecyclerView rvTransactionGroup;
    List<TransactionGroup> data;
    List<TransactionGroup> incomesData;
    List<TransactionGroup> expensesData;
    SelectTransactionGroupListViewAdapter adapter;
    TextView tvCancel, tvIncome, tvExpense;
    LinearLayout btnIncome, btnExpense, btnGroupWrapper;

    DatabaseAccess database;
    
    int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transaction_group);
        
        findView();
        init();
    }

    private void init() {

        database = new DatabaseAccess(SelectTransactionGroup.this);
        data = database.getAllGroup();

        incomesData = new ArrayList<>();
        expensesData = new ArrayList<>();

        for (TransactionGroup transactionGroup : data){
            if (transactionGroup.getType() == TransactionGroup.INCOME){
                incomesData.add(transactionGroup);
            } else {
                expensesData.add(transactionGroup);
            }
        }

        data.clear();
        data.addAll(incomesData);

        adapter = new SelectTransactionGroupListViewAdapter(SelectTransactionGroup.this,data);

        currentTab = 1;

        rvTransactionGroup.setLayoutManager(new LinearLayoutManager(SelectTransactionGroup.this));
        rvTransactionGroup.setAdapter(adapter);

        adapter.setOnItemClickedListener(new SelectTransactionGroupListViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(String groupName) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("groupName", groupName);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTab == 2){
                    btnIncome.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvIncome.setTextColor(Color.WHITE);

                    btnExpense.setBackgroundColor(Color.WHITE);
                    tvExpense.setTextColor(Color.BLACK);
                    btnExpense.setBackgroundResource(R.drawable.border_bg);

                    currentTab = 1;
                    data.clear();
                    data.addAll(incomesData);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTab == 1){
                    btnExpense.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvExpense.setTextColor(Color.WHITE);

                    btnIncome.setBackgroundColor(Color.WHITE);
                    tvIncome.setTextColor(Color.BLACK);

                    btnIncome.setBackgroundResource(R.drawable.border_bg);

                    currentTab = 2;
                    data.clear();
                    data.addAll(expensesData);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void findView() {
        rvTransactionGroup = findViewById(R.id.rvTransactionGroup);
        tvCancel = findViewById(R.id.tvCancel);
        tvIncome = findViewById(R.id.tvIncome);
        tvExpense = findViewById(R.id.tvExpense);
        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        btnGroupWrapper = findViewById(R.id.btnGroupWrapper);
    }
}