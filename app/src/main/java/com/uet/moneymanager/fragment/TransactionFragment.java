package com.uet.moneymanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.activity.TransactionDetailActivity;
import com.uet.moneymanager.adapter.TransactionListViewAdapter;
import com.uet.moneymanager.database.DatabaseAccess;
import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionDate;
import com.uet.moneymanager.model.TransactionStatistic;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class TransactionFragment extends Fragment implements View.OnClickListener, TransactionListViewAdapter.OnTransactionItemClickListener {

    private static final int ADD_TRANS_RESULT_CODE = 1;

    private Date currentDate;
    private TextView tvTotalMoney, tvPreviousDate, tvNextPage, tvCurrentPage, tvNoTransaction;

    DatabaseAccess databaseAccess;

    ArrayList<Object> data;

    RecyclerView rvTransaction;

    TransactionListViewAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_transaction, container, false);

        rvTransaction = view.findViewById(R.id.rvTransaction);

        view.findViewById(R.id.transaction_prev_page).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.transaction_next_page).setOnClickListener((View.OnClickListener) this);

        tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
        tvPreviousDate = view.findViewById(R.id.tvPreviousPage);
        tvNextPage = view.findViewById(R.id.tvNextPage);
        tvCurrentPage = view.findViewById(R.id.tvCurrentPage);
        tvNoTransaction = view.findViewById(R.id.tvNoTransaction);
        swipeRefreshLayout = view.findViewById(R.id.srlTransaction);

        currentDate = Calendar.getInstance().getTime();

        databaseAccess = new DatabaseAccess(getActivity());
        data = new ArrayList<>();

        adapter = new TransactionListViewAdapter(getActivity(), data);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransaction.setAdapter(adapter);

        adapter.setOnItemClickedListener((TransactionListViewAdapter.OnTransactionItemClickListener) this);

        getAndFillData();
        updatePagesTitle();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAndFillData();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.transaction_next_page:
                btnNextPageClick();
                break;
            case R.id.transaction_prev_page:
                btnPrevPageClick();
                break;
        }
    }



    private void btnNextPageClick() {

        int currentMonth = DateUtil.getMonth(currentDate);
        int currentYear = DateUtil.getYear(currentDate);

        int nowInMonth = DateUtil.getMonth(Calendar.getInstance().getTime());
        int nowInYear = DateUtil.getYear(Calendar.getInstance().getTime());

        if(nowInYear > currentYear || (nowInYear == currentYear && nowInMonth == currentMonth)){
            currentDate = DateUtil.getNextMonth(currentDate);
            updatePagesTitle();
            getAndFillData();
        }
    }

    private void btnPrevPageClick() {
        currentDate  = DateUtil.getPrevMonth(currentDate);
        updatePagesTitle();
        getAndFillData();

    }

    private void updatePagesTitle() {
        Date nextMonth = DateUtil.getNextMonth(currentDate);
        Date prevMonth = DateUtil.getPrevMonth(currentDate);

        String nMonth = DateUtil.formatDateBaseOnMonth(nextMonth);
        String pMonth = DateUtil.formatDateBaseOnMonth(prevMonth);
        String currentMonth = DateUtil.formatDateBaseOnMonth(currentDate);

        tvPreviousDate.setText(pMonth);
        tvNextPage.setText(nMonth);
        tvCurrentPage.setText(currentMonth);
    }

    private void getAndFillData(){
        swipeRefreshLayout.setRefreshing(false);
        Date firstDay = DateUtil.getFirstDayOfThisMonth(currentDate);
        Date lastDay = DateUtil.getLastDayOfThisMonth(currentDate);
        List<Transaction> transactions = databaseAccess.getTransactionInRange(firstDay, lastDay) ;

        int initialAmount = databaseAccess.getInitialAmount(firstDay);
        int amountOfThisMonth = databaseAccess.getMoneyAmountTransactionInRange(firstDay,lastDay);

        int currentMoney = databaseAccess.getAllMoney();

        data.clear();

        data.add(new TransactionStatistic(initialAmount,amountOfThisMonth));

        if(transactions.size() != 0) {
            rvTransaction.setVisibility(View.VISIBLE);
            tvNoTransaction.setVisibility(View.GONE);
            int currentDay = -1;
            for (Transaction transaction : transactions){
                if (DateUtil.getDayOfYear(transaction.getDate()) != currentDay){
                    currentDay = DateUtil.getDayOfYear(transaction.getDate());
                    data.add(new TransactionDate(transaction.getDate(), databaseAccess.getMoneyInADay(transaction.getDate())));
                }
                data.add(transaction);
            }
            adapter.notifyDataSetChanged();
        } else {
            rvTransaction.setVisibility(View.GONE);
            tvNoTransaction.setVisibility(View.VISIBLE);
        }

        tvTotalMoney.setText(Format.intToString(currentMoney));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_TRANS_RESULT_CODE && resultCode == Activity.RESULT_OK){
            assert data != null;
            Transaction transaction = (Transaction) Objects.requireNonNull(data.getExtras()).getSerializable("result");
            assert transaction != null;
            databaseAccess.insertTransaction(transaction);
            getAndFillData();
        }
    }

    @Override
    public void onTransactionItemClick(int transactionId) {
        Intent intent = new Intent(getActivity(), TransactionDetailActivity.class);
        intent.putExtra("id", transactionId);
        startActivity(intent);
    }
}