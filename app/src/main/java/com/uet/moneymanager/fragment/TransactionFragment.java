package com.uet.moneymanager.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.moneymanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TransactionFragment extends Fragment implements View.OnClickListener {

    private static final int ADD_TRANS_RESULT_CODE = 1;

    private Date currentDate;
    private TextView tvTotalMoney, tvPreviousDate, tvNextPage, tvCurrentPage, tvNoTransaction;

    ArrayList<Object> data;

    RecyclerView rvTransaction;
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

        currentDate = Calendar.getInstance().getTime();

        data = new ArrayList<>();


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.transaction_next_page:
            btnNextPageClick();
            break;
        }
    }

    private void btnNextPageClick() {

    }
}