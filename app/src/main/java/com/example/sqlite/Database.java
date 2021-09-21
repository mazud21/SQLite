package com.example.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "EmployeeDatabase.db", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertData(String id, String name, String salary) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("salary", salary);
        sqLiteDatabase.insert("emp", null, values);
    }

    public void updateData(String id, String name, String salary) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("name", name);
        values.put("salary", salary);
        String q = "update emp set name='"+ name +"', salary='" + salary + "' where id='" +id+"'";
        sqLiteDatabase.execSQL(q);
    }

    public ArrayList fetchData() {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        String fetchdata = "select * from emp";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);
        if (cursor.moveToFirst()) {
            do {
                stringArrayList.add(cursor.getString(0));
                stringArrayList.add(cursor.getString(1));
                stringArrayList.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEmp = "create table emp(id text,name text,salary text)";
        db.execSQL(tableEmp);
    }
}
