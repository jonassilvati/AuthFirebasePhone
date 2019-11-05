package com.jonas.firebaseauth.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    private SQLiteDatabase db;

    public DB(Context context){
        db = new DBCore(context).getWritableDatabase();
    }

    public SQLiteDatabase getDb(){
        return db;
    }
}
