package com.uet.moneymanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.database.DatabaseAccess;
import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.Format;
import com.uet.moneymanager.util.GroupIconUtil;

import java.util.Objects;

public class TransactionDetailActivity extends AppCompatActivity implements View.OnClickListener {

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

        findView();

        databaseAccess = new DatabaseAccess(TransactionDetailActivity.this);

        id = getIntent().getIntExtra("id", 0);
        Transaction transaction = databaseAccess.getTransactionById(id);

        tvTransDetailDate.setText(DateUtil.formatDate(transaction.getDate()));
        tvTransDetailMoney.setText(Format.intToString(transaction.getAmount()));
        tvTransDetailNote.setText(transaction.getNote());
        tvTransDetailGroup.setText(transaction.getTransactionGroup().getName());
        ivTransDetailGroupLogo.setImageResource(GroupIconUtil.getGroupIcon(transaction.getTransactionGroup().getName()));

        btnBack.setOnClickListener(this);
        btnEditTransaction.setOnClickListener(this);
    }

    private void findView() {
        btnBack = findViewById(R.id.btnBack);
        btnEditTransaction = findViewById(R.id.btnEditTransaction);
        tvTransDetailDate = findViewById(R.id.tvTransDetailDate);
        tvTransDetailMoney = findViewById(R.id.tvTransDetailMoney);
        tvTransDetailNote = findViewById(R.id.tvTransDetailNote);
        tvTransDetailGroup = findViewById(R.id.tvTransDetailGroup);
        ivTransDetailGroupLogo = findViewById(R.id.ivTransDetailGroupLogo);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnEditTransaction:
                Intent intent  = new Intent(TransactionDetailActivity.this, EditTransaction.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, EDIT_TRANSACTION_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TRANSACTION_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Transaction transaction = (Transaction) Objects.requireNonNull(data.getExtras()).getSerializable("result");
            assert transaction != null;
            databaseAccess.updateTransaction(transaction);

            tvTransDetailDate.setText(DateUtil.formatDate(transaction.getDate()));
            tvTransDetailMoney.setText(Format.intToString(transaction.getAmount()));
            tvTransDetailNote.setText(transaction.getNote());
            tvTransDetailGroup.setText(transaction.getTransactionGroup().getName());
            ivTransDetailGroupLogo.setImageResource(GroupIconUtil.getGroupIcon(transaction.getTransactionGroup().getName()));

        }
    }
}