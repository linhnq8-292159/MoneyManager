package com.uet.moneymanager.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uet.moneymanager.R;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    BottomSheetListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_bottom_sheet, container, false);


        TextView btnThisMonth = rootView.findViewById(R.id.btnThisMonth);
        TextView btnPrevMonth = rootView.findViewById(R.id.btnPrevMonth);
        TextView btnCustomized = rootView.findViewById(R.id.btnCustomized);

        btnThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener("1");
                dismiss();
            }
        });
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener("2");
                dismiss();
            }
        });
        btnCustomized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener("3");
                dismiss();
            }
        });
        return rootView;
    }


    public interface BottomSheetListener {
        void onItemClickListener(String position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " a");
        }
    }
}