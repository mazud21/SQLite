package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String ParsingDta = "{\"data\": [\n" +
            "{\n" +
            "\"id_tarif_air\": \"1\",\n" +
            "\"tarif\": \"2750\",\n" +
            "\"ket_tarif\": \"Tipe A1(0-10 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"2\",\n" +
            "\"tarif\": \"3900\",\n" +
            "\"ket_tarif\": \"Tipe A1(11-20 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"3\",\n" +
            "\"tarif\": \"4400\",\n" +
            "\"ket_tarif\": \"Tipe A1(>20 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"4\",\n" +
            "\"tarif\": \"4500\",\n" +
            "\"ket_tarif\": \"Tipe A2(0-10 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"5\",\n" +
            "\"tarif\": \"4800\",\n" +
            "\"ket_tarif\": \"Tipe A2(11-20 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"6\",\n" +
            "\"tarif\": \"5400\",\n" +
            "\"ket_tarif\": \"Tipe A2(>20 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"7\",\n" +
            "\"tarif\": \"2500\",\n" +
            "\"ket_tarif\": \"Sosial Khusus(0-10 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"8\",\n" +
            "\"tarif\": \"3400\",\n" +
            "\"ket_tarif\": \"Sosial Khusus(11-20 m3)\"\n" +
            "},\n" +
            "{\n" +
            "\"id_tarif_air\": \"9\",\n" +
            "\"tarif\": \"4100\",\n" +
            "\"ket_tarif\": \"Sosial Khusus(>20 m3)\"\n" +
            "}\n" +
            "]}";
    TextView textView1;
    ArrayList arrayList;
    String str = "";
    ListView listView;
    Database database;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(MainActivity.this);
        // this.deleteDatabase("EmployeeDatabase.db");
        database.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();;

        textView1 = findViewById(R.id.textView1);
        listView = findViewById(R.id.listView);
        try {
            JSONObject jsonObject = new JSONObject(ParsingDta);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String id = jsonObject1.getString("id_tarif_air");
                String name = jsonObject1.getString("tarif");
                String salary = jsonObject1.getString("ket_tarif");

                cursor = sqLiteDatabase.rawQuery("SELECT * FROM emp",null);
                cursor.moveToFirst();
                //database.insertData(id, name, salary);
                database.updateData(id, name, salary);
                /*if (cursor.getCount()>0){
                    database.updateData(id, name, salary);
                } else {
                    database.insertData(id, name, salary);
                }*/
                str += "\n Employee" + i + "\n id_tarif_air:" + name + "\n tarif:" + id + "\n ket_tarif:" + salary + "\n";
                //textView1.setText(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayList = database.fetchData();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.activity_list_item, android.R.id.text1, arrayList);
        listView.setAdapter(adapter);
    }
}