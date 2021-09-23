package com.example.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "sq_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableSales = "create table sales(SaANo text,SaNm text,SaLsUsr text)";
        db.execSQL(tableSales);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertData(String SaANo, String SaNm, String SaLsUsr) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SaANo", SaANo);
        values.put("SaNm", SaNm);
        values.put("SaLsUsr", SaLsUsr);

        sqLiteDatabase.insert("sales", null, values);

    }

    public void updateData(String SaANo, String SaNm, String SaLsUsr) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("SaANo", SaANo);
        values.put("SaNm", SaNm);
        values.put("SaLsUsr", SaLsUsr);
        String q = "update sales set SaNm='"+ SaNm +"', SaLsUsr='" + SaLsUsr + "' where SaANo='" +SaANo+"'";
        sqLiteDatabase.execSQL(q);
    }

    public ArrayList fetchData() {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        String fetchdata = "select SaNm || '-' || SaANo from sales";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(fetchdata, null);
        if (cursor.moveToFirst()) {
            do {
                stringArrayList.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }

}
