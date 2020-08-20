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

}


