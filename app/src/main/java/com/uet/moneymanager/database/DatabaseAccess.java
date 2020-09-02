package com.uet.moneymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.DateUtil;
import com.uet.moneymanager.util.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context){
        this.sqLiteOpenHelper =new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        if(database !=null){
            this.database.close();
        }
    }


    public List<Transaction> getAllTransaction() {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM Transactions INNER JOIN Groups on Transactions.group_id = Groups.id ORDER by date desc";

        List<Transaction> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                int amount = c.getInt(c.getColumnIndex("amount"));
                String note = c.getString(c.getColumnIndex("note"));
                //int walletType = c.getInt(c.getColumnIndex(TRANSACTION_WALLET_TYPE));
                int groupId = c.getInt(c.getColumnIndex("group_id"));
                Date date = Format.timestampToDate(c.getLong(c.getColumnIndex("date")));

                lst.add(new Transaction(id, amount, getGroupById(groupId), note, date));
                c.moveToNext();
            }
            c.close();
            db.close();
            return lst;
        }
    }


    public TransactionGroup getGroupById(int id) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM Groups WHERE id" + " = " + id;

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            TransactionGroup group = null;
            if (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex("name"));
                int type = c.getInt(c.getColumnIndex("type"));
                group = new TransactionGroup(id, name, type);
            }
            c.close();
            db.close();
            return group;
        }
    }
    public List<Transaction> getTransactionInRange(Date startDate, Date endDate) {
        long startDateInMilliseconds = DateUtil.getStartDayTime(startDate);
        long endDateInMilliseconds = DateUtil.getEndDayTime(endDate);

        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM Transactions WHERE date >=" + startDateInMilliseconds +"AND date <" +endDateInMilliseconds +"ORDER BY date DESC";

        List<Transaction> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while  (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                int amount = c.getInt(c.getColumnIndex("amount"));
                String note = c.getString(c.getColumnIndex("date"));
                int groupId = c.getInt(c.getColumnIndex("group_id"));
                Date date = Format.timestampToDate(c.getLong(c.getColumnIndex("date")));
                lst.add(new Transaction(id, amount, getGroupById(groupId), note, date));
                c.moveToNext();
            }
            c.close();
            db.close();
            return lst;
        }
    }
    public int getMoneyAmountTransaction(Date date){
        long dateInMilliseconds = DateUtil.getStartDayTime(date);

        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query;
        query = "SELECT * FROM  Transactions WHERE  date  >= " + dateInMilliseconds;
        int result = 0;

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return result;
        } else {
            c.moveToFirst();
            Transaction transaction = null;
            while (!c.isAfterLast()) {
                float amount = c.getFloat(c.getColumnIndex("amount"));
                result += amount;
                c.moveToNext();
            }
            c.close();

            return result;
        }
    }

//    public void insertTransaction(Transaction transaction) {
//        //System.out.println("Deleting ads that is marked as to be deleted..");
//        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
//        db.execSQL(getInsertQuery(TABLE_TRANSACTION, prepareInsertIntoTableTransaction((int)transaction.getMoneyAmount(), transaction.getGroup().getId(), transaction.getNote(), transaction.getDate().getTime(), transaction.getWalletType())));
//        long currentMoney = getCurrentMoney(transaction.getWalletType());
//        if (transaction.getWalletType() == WalletType.BANK_CARD) {
//            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
//        } else {
//            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
//        }
//
//    }

    public void getinsertTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put("amount",transaction.getAmount());
        values.put("note",transaction.getNote());
        values.put("date", String.valueOf(transaction.getDate()));
        values.put("group_id",transaction.getTransactionGroup().getId());
        database.insert("Transactions", null, values);
    }

    public void getupdateTransaction(Transaction oldtransaction,Transaction newtransaction) {
        ContentValues values = new ContentValues();
        values.put("amount",newtransaction.getAmount());
        values.put("note",newtransaction.getNote());
        values.put("date", String.valueOf(newtransaction.getDate()));
        values.put("group_id",newtransaction.getTransactionGroup().getId());
        database.update("Transactions",values,"id = ?",new String[]{String.valueOf(oldtransaction.getId())});
    }

    public void deleteTransaction(Transaction transaction) {
        database.delete("Transactions", "id = ?", new String[]{String.valueOf(transaction.getId())});
        database.close();
    }


    public List<TransactionGroup> getAllGroup() {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM Groups";

        List<TransactionGroup> list = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c==null){
            return list;
        }
        else  {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                int type = c.getInt(c.getColumnIndex("type"));
                list.add(new TransactionGroup(id, name, type));
                c.moveToNext();
            }
            c.close();
            db.close();
        }
        return list;
    }

    public TransactionGroup getGroupByGroupName(String groupName) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM Groups WHERE name = "+"'" + groupName + "'";
        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            TransactionGroup group = null;
            if (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                int type = c.getInt(c.getColumnIndex("type"));
                group = new TransactionGroup(id, name, type);
            }
            c.close();
            db.close();
            return group;
        }
    }

}
