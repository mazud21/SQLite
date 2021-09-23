package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    Button button;
    Database dbSQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //START config SQLite
        dbSQ = new Database(SplashScreen.this);

        getSalesAll();
        //END config SQLite

        button = findViewById(R.id.btnNext);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void getSalesAll() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.50.2/api/getsalesall/", response -> {
            //mProgressDialog.show();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                Log.d("TAG_SALES_ALL", "onResponse: " + jsonObject);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String SaANo = jsonObject1.getString("SaANo");
                    String SaNm = jsonObject1.getString("SaNm");
                    String SaLsUsr = jsonObject1.getString("SaLsUsr");

                    dbSQ.insertData(SaANo, SaNm, SaLsUsr);

                    /*Cursor cur = sqlite.rawQuery("SELECT COUNT(*) FROM sales", null);
                    if (cur != null) {
                        cur.moveToFirst(); // Always one row returned.
                        //kalau sudah terisi, panggil fungsi update
                        dbSQ.updateData(SaANo, SaNm, SaLsUsr);
                        if (cur.getInt(0) == 0) { // Zero count means empty table.
                            //jika data tabel sales belum terisi, maka lakukan insert data
                            dbSQ.insertData(SaANo, SaNm, SaLsUsr);
                        }
                    }*/

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG_SALES_ALL", "onResponse: " + e.toString());
            }

        }, error -> {
            Log.d("TAG_SALES_ERROR", "onResponse: " + error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("key", "suksesmandiri96");
                params.put("ip", "");
                return params;
            }
        };
        Volley.newRequestQueue(getBaseContext()).add(stringRequest);

    }

}