package com.uet.moneymanager.util;

import  com.uet.moneymanager.R;

public class GroupIconUtil {
    public static int getGroupIcon(String groupName){
        switch (groupName){
            case "Ăn uống":
                return R.drawable.ic_eat;
            case "Cafe":
            case "Tiệc":
                return R.drawable.ic_coffee;
            case "Tiền điện":
                return R.drawable.ic_bill;
            case "Khoản chi khác":
            case "Khoản thu khác":
                return R.drawable.ic_money_other;
            case "Tiền nước":
                return R.drawable.ic_water_bill;
            case "Thẻ điện thoại":
                return R.drawable.ic_payment;
            case "Internet":
                return R.drawable.ic_wifi;
            case "Thuê nhà":
            case "Sửa chữa nhà cửa":
                return R.drawable.ic_house;
            case "Xăng xe":
                return R.drawable.ic_car;
            case "Thời trang":
                return R.drawable.ic_fashion;
            case "Thiết bị điện tử":
                return R.drawable.ic_electronic;
            case "Bạn bè & Người yêu":
                return R.drawable.ic_love;
            case "Xem phim":
                return R.drawable.ic_entertainment;
            case "Du lịch":
                return R.drawable.ic_travel;
            case "Thuốc":
                return R.drawable.ic_medicine;
            case "Chăm sóc cá nhân":
                return R.drawable.ic_doctor;
            case "Con cái":
                return R.drawable.ic_children;
            case "Sách":
                return R.drawable.ic_book;
            case "Rút tiền":
            case "Bán đồ":
                return R.drawable.ic_sell;
            case "Lương":
                return R.drawable.ic_salary;
            case "Thưởng":
                return R.drawable.ic_bonus;
            case "Tiền lãi":
                return R.drawable.ic_interest_rate;
            case "Vay tiền":
                return R.drawable.ic_loan;
            default:
                return R.drawable.ic_money;
        }
    }
}
