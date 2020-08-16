package com.uet.moneymanager.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Format {
    public static String doubleToString(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(num) + " đ";
    }
    public static String doubleToString(double num, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(num);
    }
    public static String intToString(int num) {
        return String.valueOf(num);
    }

    public static int stringToInt(String str, int defNum) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return defNum;
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "vn"));
        return sdf.format(date);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "vn"));
        return sdf.format(date);
    }

    public static long dateToTimestamp(Date date) {
        return date.getTime();
    }

    public static Date timestampToDate(long timestamp) {
        Timestamp stamp = new Timestamp(timestamp);
        return new Date(stamp.getTime());
    }

    public static long stringToLong(String numberInString) {
        return Long.parseLong(numberInString);
    }
}
