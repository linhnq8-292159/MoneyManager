package com.uet.moneymanager.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.sqLiteOpenHelper = new DatabaseOpenHelper(context);
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
        if (database != null){
            this.database.close();
        }
    }


}
