package com.uet.moneymanager.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moneymanager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRANSACTION = "TBL_TRANSACTION";
    private static final String TABLE_GROUP = "TABLE_GROUP";

    //Table transaction
    private static final String TRANSACTION_ID = "ID";
    private static final String TRANSACTION_AMOUNT = "AMOUNT";
    private static final String TRANSACTION_GROUP_ID = "GROUP_ID";
    private static final String TRANSACTION_NOTE = "NOTE";
    private static final String TRANSACTION_DATE = "DATE";

    //Table group
    private static final String GROUP_ID = "ID";
    private static final String GROUP_NAME = "NAME";
    private static final String GROUP_TYPE = "TYPE";



    public Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private Map<String, String> prepareCreateTableTransaction() {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        mp.put(TRANSACTION_AMOUNT, " INTEGER, ");
        mp.put(TRANSACTION_GROUP_ID, " INTEGER, ");
        mp.put(TRANSACTION_NOTE, " TEXT, ");
        mp.put(TRANSACTION_DATE, " INTEGER, ");
        return mp;
    }

    private Map<String, String> prepareCreateTableGroup() {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_ID, " INTEGER PRIMARY KEY, ");
        mp.put(GROUP_NAME, " TEXT, ");
        mp.put(GROUP_TYPE, " INTEGER");
        return mp;
    }

    private Map<String, String> prepareInsertIntoTableTransaction(int amount, int groupId, String note, long date) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_AMOUNT + ", ", amount + ", ");
        mp.put(TRANSACTION_GROUP_ID + ", ", groupId + ", ");
        mp.put(TRANSACTION_NOTE + ", ", "'" + note + "', ");
        mp.put(TRANSACTION_DATE + ", ", date + ", ");
        return mp;
    }

    private Map<String, String> prepareInsertIntoTableGroup(String groupName, int groupType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_NAME + ", ", "'" + groupName + "', ");
        mp.put(GROUP_TYPE, groupType + "");
        return mp;
    }

    private Map<String, String> prepareUpdateTableTransaction(int amount, int groupId, String note, long date) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_AMOUNT, amount + ", ");
        mp.put(TRANSACTION_GROUP_ID, groupId + ", ");
        mp.put(TRANSACTION_NOTE, "'" + note + "', ");
        mp.put(TRANSACTION_DATE, date + "");
        return mp;
    }

    private Map<String, String> prepareUpdateTableGroup(String groupName, int groupType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_NAME, "'" + groupName + "', ");
        mp.put(GROUP_TYPE, groupType + "");
        return mp;
    }

    private String getCreateQuery(String tableName, Map<String, String> tableFields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableName).append("(");
        for (Map.Entry<String, String> entry : tableFields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(" ").append(value);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private String getInsertQuery(String tableName, Map<String, String> values) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("INSERT INTO ").append(tableName).append("(");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" VALUES(");

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder1.append(key);
            stringBuilder2.append(value);
        }
        stringBuilder1.append(")");
        stringBuilder2.append(")");
        return stringBuilder1.toString() + stringBuilder2.toString();
    }

    private String getDeleteQuery(String tableName, int id) {
        return "DELETE FROM " + tableName + " WHERE id = " + id;
    }

    private String getUpdateQuery(String tableName, Map<String, String> values, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ").append(tableName).append(" SET ");

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(" = ").append(value);
        }
        stringBuilder.append(" where id = ").append(id);

        return stringBuilder.toString();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create table transaction
        sqLiteDatabase.execSQL(getCreateQuery(TABLE_TRANSACTION, prepareCreateTableTransaction()));

        //Create table group
        sqLiteDatabase.execSQL(getCreateQuery(TABLE_GROUP, prepareCreateTableGroup()));

        ArrayList<String> outGoingList = new ArrayList<>(Arrays.asList("Ăn uống", "Cafe", "Tiền điện", "Tiền nước", "Thẻ điện thoại", "Internet", "Thuê nhà", "Xăng xe", "Thời trang", "Thiết bị điện tử", "Bạn bè & Người yêu", "Xem phim", "Du lịch", "Thuốc", "Chăm sóc cá nhân", "Tiệc", "Con cái", "Sửa chữa nhà cửa", "Sách", "Rút tiền", "Khoản chi khác"));
        ArrayList<String> inComingList = new ArrayList<>(Arrays.asList("Lương", "Thưởng", "Tiền lãi", "Bán đồ", "Vay tiền", "Khoản thu khác"));

        for (String item : outGoingList) {
            sqLiteDatabase.execSQL(getInsertQuery(TABLE_GROUP, prepareInsertIntoTableGroup(item, TransactionGroup.EXPENSE)));
        }

        for (String item : inComingList) {
            sqLiteDatabase.execSQL(getInsertQuery(TABLE_GROUP, prepareInsertIntoTableGroup(item, TransactionGroup.INCOME)));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(sqLiteDatabase);

    }

    public TransactionGroup getGroupById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GROUP + " WHERE " + GROUP_ID + " = " + id;

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            TransactionGroup group = null;
            if (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex(GROUP_NAME));
                int type = c.getInt(c.getColumnIndex(GROUP_TYPE));
                group = new TransactionGroup(id, name, type);
            }
            c.close();

            return group;
        }
    }

    public List<TransactionGroup> getAllGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GROUP;

        List<TransactionGroup> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(GROUP_ID));
                String name = c.getString(c.getColumnIndex(GROUP_NAME));
                int type = c.getInt(c.getColumnIndex(GROUP_TYPE));
                lst.add(new TransactionGroup(id, name, type));
                c.moveToNext();
            }
            c.close();
            //System.out.println(lst.size() + " ads file to be deleted");
            return lst;
        }
    }

    public Transaction getTransactionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_ID + " = " + id;
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            Transaction transaction = null;
            if (!c.isAfterLast()) {

                Double amount = c.getDouble(c.getColumnIndex(TRANSACTION_AMOUNT));
                String note = c.getString(c.getColumnIndex(TRANSACTION_NOTE));
                int groupId = c.getInt(c.getColumnIndex(TRANSACTION_GROUP_ID));
                Date date = Format.timestampToDate(c.getLong(c.getColumnIndex(TRANSACTION_DATE)));
                transaction = new Transaction(id, amount, getGroupById(groupId), note, date );
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return transaction;
        }
    }



}
