package com.uet.moneymanager.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static int getNumberOfDays(Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getNextMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.DAY_OF_MONTH) < 28){
            calendar.add(Calendar.DAY_OF_YEAR, 28 - Calendar.DAY_OF_MONTH);
        }
        while (calendar.get(Calendar.DAY_OF_MONTH) != 1){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        Timestamp timestamp = new Timestamp(getStartDayTime(calendar.getTime()));
        return new Date(timestamp.getTime());
    }

    public static Date getPrevMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -calendar.get(Calendar.DAY_OF_MONTH) - 27);
        while (calendar.get(Calendar.DAY_OF_MONTH) != 1){
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        Timestamp timestamp = new Timestamp(getStartDayTime(calendar.getTime()));
        return new Date(timestamp.getTime());
    }

    public static int getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static long getStartDayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return calendar1.getTimeInMillis();
    }
    public static long getEndDayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return calendar1.getTime().getTime();
    }

    public static long getEndDayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Calendar cal = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return cal.getTime().getTime();
    }

    public static String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Thứ Hai";
            case Calendar.TUESDAY:
                return "Thứ Ba";
            case Calendar.WEDNESDAY:
                return "Thứ Tư";
            case Calendar.THURSDAY:
                return "Thứ Năm";
            case Calendar.FRIDAY:
                return "Thứ Sáu";
            case Calendar.SATURDAY:
                return "Thứ Bảy";
            case Calendar.SUNDAY:
                return "Chủ Nhật";
            default:
                return "Thứ ";
        }
    }

    public static String getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
    }

    public static String getMonthAndYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return month + "/" + year;
    }

    public static String formatDate(Date date) {
        return getDayOfWeek(date) + ", " + getDayOfMonth(date) + "/" + getMonthAndYear(date);
    }

    public static String formatDateBaseOnMonth(Date date) {
        if (Calendar.getInstance().getTimeInMillis() < date.getTime()) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            return "T" + month + "/" + year;
        }
    }

    public static Date getFirstDayOfThisMonth(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        while (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        return Format.timestampToDate(getStartDayTime(calendar.getTime()));
    }

    public static Date getLastDayOfThisMonth(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        while (calendar.get(Calendar.DAY_OF_MONTH) != getNumberOfDays(currentDate)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return Format.timestampToDate(getStartDayTime(calendar.getTime()));
    }

}


