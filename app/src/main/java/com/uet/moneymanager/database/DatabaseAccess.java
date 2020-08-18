package com.uet.moneymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.uet.moneymanager.model.Transaction;
import com.uet.moneymanager.model.TransactionGroup;
import com.uet.moneymanager.util.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    public List<Transaction> getTransaction() {
//        List<Transaction> list = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM ransaction", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Transaction transaction = new Transaction();
//            transaction.setAmount(Integer.parseInt(cursor.getString(0)));
//            transaction.setNote(cursor.getString(1));
//            transaction.setTransactionGroup(cursor.getString(2));
//            transaction.setDate(cursor.getColumnIndex("date"));
//            list.add(transaction);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }

    public Transaction getTransactionById(int id) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM  Transaction WHERE "+ "id" + " = " + id;

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            Transaction transaction = null;
            if (!c.isAfterLast()) {

                int amount =  c.getInt(c.getColumnIndex("amount"));
                String note = c.getString(c.getColumnIndex("note"));

                int groupId = c.getInt(c.getColumnIndex("TransactionGruop_id"));
                Date date = Format.timestampToDate(c.getLong(c.getColumnIndex("date")));
                transaction = new Transaction(id, amount, getGroupById(groupId), note, date);
            }
            c.close();

            return transaction;
        }
    }

    public TransactionGroup getGroupById(int id) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "Group" + " WHERE " + "id" + " = " + id;
        //System.out.println(query);

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
            //System.out.println("Ads retrieved: " + ads);
            return group;
        }
    }

    public void insertTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put("amount",transaction.getAmount());
        values.put("note",transaction.getNote());
        values.put("date", String.valueOf(transaction.getDate()));
        database.insert("Transaction", null, values);
    }

    public void updateTransaction(Transaction oldtransaction,Transaction newtransaction) {
        ContentValues values = new ContentValues();
        values.put("amount",newtransaction.getAmount());
        values.put("note",newtransaction.getNote());
        values.put("date", String.valueOf(newtransaction.getDate()));
        database.update("Transaction",values,"id = ?",new String[]{String.valueOf(oldtransaction.getId())});
    }

    public void deleteTransaction(Transaction transaction) {
        database.delete("Transaction", "phone = ?", new String[]{String.valueOf(transaction.getId())});
        database.close();
    }
}
