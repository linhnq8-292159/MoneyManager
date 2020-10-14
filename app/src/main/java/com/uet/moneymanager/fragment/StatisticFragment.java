package com.uet.moneymanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.uet.moneymanager.R;
import com.uet.moneymanager.activity.SelectDate;
import com.uet.moneymanager.database.DatabaseAccess;
import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StatisticFragment extends Fragment {

    public static final int SELECT_NEW_RANGE = 1;

    TextView tvStartDate, tvEndDate, tvInitialAmount, tvFinalAmount;
    private PieChart pieChart;
    private int monthTotalIncomeAmount;
    private int monthTotalExpenseAmount;

    final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
            Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};

    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseAccess database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);
        pieChart  = rootView.findViewById(R.id.pieChart);
        swipeRefreshLayout = rootView.findViewById(R.id.srlReporting);
        tvStartDate = rootView.findViewById(R.id.tvStartDate);
        tvEndDate = rootView.findViewById(R.id.tvEndDate);


        database = new DatabaseAccess(getActivity());


        Date now = Calendar.getInstance().getTime();
        Date firstDay = DateUtil.getFirstDayOfThisMonth(now);
        Date lastDay = DateUtil.getLastDayOfThisMonth(now);
        updateData(firstDay, lastDay);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                drawChart();
            }
        });
        return rootView;
    }

    private void drawChart() {
        PieDataSet pieDataSet = new PieDataSet(getData(), "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(500, 500);
        pieChart.invalidate();
        pieChart.setDrawCenterText(false);
        pieChart.setDrawEntryLabels(false);

        swipeRefreshLayout.setRefreshing(false);
    }

    private List<PieEntry> getData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(monthTotalIncomeAmount, "Khoản thu"));
        entries.add(new PieEntry(monthTotalExpenseAmount, "Khoản chi"));
        return entries;
    }

    private void updateData(Date firstDay, Date lastDay) {
        tvStartDate.setText(Format.dateToString(firstDay));



        List<Transaction> transactions = database.getTransactionInRange(firstDay, lastDay);

        monthTotalExpenseAmount = 0;
        monthTotalIncomeAmount = 0;
        for(Transaction transaction : transactions) {
            if (transaction.getTransactionGroup().getType() == TransactionGroup.EXPENSE){
                monthTotalIncomeAmount += Math.abs(transaction.getAmount());
            } else {
                monthTotalExpenseAmount += Math.abs(transaction.getAmount());
            }
        }
        drawChart();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_NEW_RANGE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Date start = (Date) Objects.requireNonNull(data.getExtras()).getSerializable("startDay");
            Date end = (Date) Objects.requireNonNull(data.getExtras()).getSerializable("endDay");
            updateData(start,end);

        }
    }
}