package com.uet.moneymanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uet.moneymanager.R;
import com.uet.moneymanager.database.DatabaseAccess;
import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.GroupIconUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener{

    public static final int SELECT_GROUP = 1;

    TextView tvDatePickerValue, tvCancelTransaction, tvSaveTransaction, tvSelectedGroup;
    ImageView ivTransactionGroupIcon;
    EditText etAmountOfMoney, etNote;
    Date selectedDate;
    DatePickerDialog pickerDialog;
    DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        findView();
        init();

    }

    private void init() {
        tvDatePickerValue.setOnClickListener(this);
        databaseAccess = new DatabaseAccess(AddTransaction.this);
        etAmountOfMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etAmountOfMoney.removeTextChangedListener(this);
                try {
                    String givenString = editable.toString();
                    long value;
                    if (givenString.contains(",")){
                        givenString = givenString.replaceAll(",","");
                    }
                    value = Long.parseLong(givenString);
                    DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                    String formatted = decimalFormat.format(value);
                    etAmountOfMoney.setText(formatted);
                    etAmountOfMoney.setSelection(etAmountOfMoney.getText().length());
                } catch (Exception e){
                    e.printStackTrace();
                }
                etAmountOfMoney.addTextChangedListener(this);
            }
        });
        tvCancelTransaction.setOnClickListener(this);
        tvSaveTransaction.setOnClickListener(this);
        selectedDate = Calendar.getInstance().getTime();
        tvSelectedGroup.setOnClickListener(this);
    }

    private void findView() {
        tvDatePickerValue = findViewById(R.id.tvDatePickerValue);
        etAmountOfMoney = findViewById(R.id.etAmountOfMoney);
        tvCancelTransaction = findViewById(R.id.tvCancelTransaction);
        tvSaveTransaction = findViewById(R.id.tvSaveTransaction);
        tvSelectedGroup = findViewById(R.id.tvSelectedGroup);
        etNote = findViewById(R.id.etTransactionNote);
        tvDatePickerValue.setText(DateUtil.formatDate(Calendar.getInstance().getTime()));
        ivTransactionGroupIcon = findViewById(R.id.ivTransactionGroupIcon);
    }

    @Override
    public void onClick(View view) {
        Intent returnIntent = new Intent();
        switch (view.getId()) {
            case R.id.tvDatePickerValue:
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                final int year = cal.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(AddTransaction.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        tvDatePickerValue.setText(DateUtil.formatDate(selectedDate));
                    }
                },year,month,day);
                pickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                pickerDialog.show();
                break;
            case R.id.tvCancelTransaction:
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
                break;
            case R.id.tvSelectedGroup:
                Intent intent = new Intent(AddTransaction.this, SelectTransactionGroup.class);
                startActivityForResult(intent, SELECT_GROUP);
                break;
            case R.id.tvSaveTransaction:
                if (etAmountOfMoney.getText().toString().equals("") || tvSelectedGroup.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Lỗi");
                    builder.setMessage("Vui lòng điền đầy đủ thông tin");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else{
                    int amount = Integer.parseInt(etAmountOfMoney.getText().toString().replace(",", ""));
                    TransactionGroup transactionGroup = databaseAccess.getGroupByGroupName(tvSelectedGroup.getText().toString());
                    String note = etNote.getText().toString();
                    Date date = selectedDate;
                    if (transactionGroup.getType() == TransactionGroup.EXPENSE){
                        amount = -amount;
                    }
                    returnIntent.putExtra("result", new Transaction(amount, transactionGroup, note, date));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_GROUP && resultCode == RESULT_OK){
            assert data !=null;
            String groupName = data.getStringExtra("groupName");
            tvSelectedGroup.setText(groupName);
            assert groupName != null;
            //lỗi dùng vector
            //ivTransactionGroupIcon.setImageResource(GroupIconUtil.getGroupIcon(groupName));
        }
    }
}