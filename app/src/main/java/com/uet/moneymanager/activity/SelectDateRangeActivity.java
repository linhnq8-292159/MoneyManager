package com.uet.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SelectDateRangeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvStartDate, tvEndDate, btnCancel, btnSave;
    LinearLayout btnSelectedStartDate, btnSelectedEndDate;
    Date startDate, endDate;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_range);
        mapView();
        init();

    }
    private void mapView() {
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        btnSelectedStartDate = findViewById(R.id.btnSelectedStartDate);
        btnSelectedEndDate = findViewById(R.id.btnSelectedEndDate);
        btnCancel = findViewById(R.id.btnCancelSelectRange);
        btnSave = findViewById(R.id.btnSaveSelectedRange);
    }
    private void init(){
        tvStartDate.setText(DateUtil.formatDate(Calendar.getInstance().getTime()));
        tvEndDate.setText(DateUtil.formatDate(Calendar.getInstance().getTime()));
        startDate = endDate = Calendar.getInstance().getTime();

        btnSelectedStartDate.setOnClickListener(this);
        btnSelectedEndDate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent returnIntent = new Intent();
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        switch (view.getId()) {
            case R.id.btnSelectedStartDate:
                picker = new DatePickerDialog(SelectDateRangeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                                tvStartDate.setText(DateUtil.formatDate(startDate));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                picker.show();
                break;
            case R.id.btnSelectedEndDate:
                picker = new DatePickerDialog(SelectDateRangeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                                tvEndDate.setText(DateUtil.formatDate(endDate));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                picker.show();
                break;
            case R.id.btnCancelSelectRange:
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                break;
            case R.id.btnSaveSelectedRange:
                returnIntent.putExtra("startDay", startDate);
                returnIntent.putExtra("endDay", endDate);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
        }
    }
}