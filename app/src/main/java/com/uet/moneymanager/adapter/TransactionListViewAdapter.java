package com.uet.moneymanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionDate;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.model.TransactionStatistic;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.Format;
import com.uet.moneymanager.util.GroupIconUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransactionListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_STATISTIC = 1;
    private static int TYPE_HEADER = 2;
    private static int TYPE_ITEM = 3;
    private Context context;
    private ArrayList<Object> data;

    public TransactionListViewAdapter(Context context, ArrayList<Object> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_STATISTIC) {
            view = LayoutInflater.from(context).inflate(R.layout.row_statistic, parent, false);
            return new TransactionStatisticViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.row_trans_header, parent, false);
            return new TransactionHeaderViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.row_trans_item, parent, false);
            return new TransactionItemViewHolder(view);
        }
        view = LayoutInflater.from(context).inflate(R.layout.row_statistic, parent, false);
        return new TransactionStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            TransactionItemViewHolder vholder = (TransactionItemViewHolder) holder;
            vholder.setTransaction((Transaction) data.get(position));
            vholder.btnTransItemWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onTransactionItemClick(((Transaction)data.get(position)).getId());
                    }
                }
            });
        } else if (getItemViewType(position) == TYPE_HEADER) {
            ((TransactionHeaderViewHolder) holder).setTransactionDate((TransactionDate) data.get(position));
        } else if (getItemViewType(position) == TYPE_STATISTIC) {

            ((TransactionStatisticViewHolder) holder).setTransactionStatistic((TransactionStatistic) data.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = data.get(position);
        //kiem tra kieu du lieu instanceof
        if (obj instanceof Transaction) {
            return TYPE_ITEM;
        } else if (obj instanceof TransactionDate) {
            return TYPE_HEADER;
        } else if (obj instanceof TransactionStatistic) {
            return TYPE_STATISTIC;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    static class TransactionStatisticViewHolder extends RecyclerView.ViewHolder{
        private TextView tvBeginMoney;
        private TextView tvEndMoney;

        public TransactionStatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBeginMoney = itemView.findViewById(R.id.tv_begin_money_value);
            tvEndMoney = itemView.findViewById(R.id.tv_end_money_value);
        }
        void setTransactionStatistic(TransactionStatistic transactionStatistic) {
            tvBeginMoney.setText(Format.intToString(transactionStatistic.getInitialAmount()));
            tvEndMoney.setText(Format.intToString(transactionStatistic.getAmountOfThisMonth()));

        }
    }
    static class TransactionHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDayOfMonth;
        private TextView tvDayOfWeek;
        private TextView tvMonth;
        private TextView tvAmount;

        public TransactionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDayOfMonth = itemView.findViewById(R.id.row_trans_header_day_of_month);
            tvDayOfWeek = itemView.findViewById(R.id.row_trans_header_day_of_week);
            tvMonth = itemView.findViewById(R.id.row_trans_header_month);
            tvAmount = itemView.findViewById(R.id.row_trans_header_amount);
        }

        void setTransactionDate(TransactionDate transactionDate) {
            tvDayOfWeek.setText(DateUtil.getDayOfWeek(transactionDate.getDate()));
            tvDayOfMonth.setText(DateUtil.getDayOfMonth(transactionDate.getDate()));
            tvAmount.setText(Format.intToString(transactionDate.getMoneyAmount()));
            tvMonth.setText(DateUtil.getMonthAndYear(transactionDate.getDate()));
        }
    }
    static class TransactionItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTransactionGroupIcon;
        private TextView tvGroupName;
        private TextView tvNote;
        private TextView tvAmount;
        private LinearLayout btnTransItemWrapper;

        TransactionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTransactionGroupIcon = itemView.findViewById(R.id.transaction_group_icon);
            tvGroupName = itemView.findViewById(R.id.row_trans_item_group_name);
            tvNote = itemView.findViewById(R.id.row_trans_item_note);
            tvAmount = itemView.findViewById(R.id.row_trans_item_amount);
            btnTransItemWrapper = itemView.findViewById(R.id.btnTransItemWrapper);
        }

        void setTransaction(Transaction transaction) {
            ivTransactionGroupIcon.setImageResource(GroupIconUtil.getGroupIcon(transaction.getTransactionGroup().getName()));
            tvGroupName.setText(transaction.getTransactionGroup().getName());
            tvNote.setText(transaction.getNote());
            tvAmount.setText(Format.intToString(transaction.getAmount()));
            if (transaction.getTransactionGroup().getType() == TransactionGroup.INCOME) {
                tvAmount.setTextColor(Color.GREEN);
            } else {
                tvAmount.setTextColor(Color.RED);
            }
        }
    }
    public interface OnTransactionItemClickListener {
        void onTransactionItemClick(int transactionId);
    }

    private OnTransactionItemClickListener mListener;

    public void setOnItemClickedListener(OnTransactionItemClickListener onItemClickedListener) {
        this.mListener = onItemClickedListener;
    }
}
