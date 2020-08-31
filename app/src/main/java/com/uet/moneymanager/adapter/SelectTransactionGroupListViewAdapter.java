package com.uet.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uet.moneymanager.R;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.GroupIconUtil;

import java.util.List;

public class SelectTransactionGroupListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TransactionGroup> data;

    private OnItemClickedListener onItemClickedListener;

    public SelectTransactionGroupListViewAdapter(Context context, List<TransactionGroup> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_transaction_group, parent, false);
        return new TransactionGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TransactionGroupViewHolder viewHolder = (TransactionGroupViewHolder) holder;
        viewHolder.setTransactionGroup(data.get(position));

        viewHolder.transactionGroupWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null){
                    onItemClickedListener.onItemClicked(viewHolder.tvName.getText().toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TransactionGroupViewHolder extends RecyclerView.ViewHolder{

        ImageView ivIcon;
        TextView tvName;
        LinearLayout transactionGroupWrapper;

        public TransactionGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivTransactionGroupIcon);
            tvName = itemView.findViewById(R.id.tvTransactionGroupName);
            transactionGroupWrapper = itemView.findViewById(R.id.transactionGroupWrapper);
        }

        void setTransactionGroup(TransactionGroup transactionGroup){
            ivIcon.setImageResource(GroupIconUtil.getGroupIcon(transactionGroup.getName()));
            tvName.setText(transactionGroup.getName());
        }

    }
    public  interface OnItemClickedListener {
        void onItemClicked(String groupName);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }
}
