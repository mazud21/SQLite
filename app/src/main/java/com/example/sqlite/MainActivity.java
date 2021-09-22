package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvNoSales, tvNamaSales;
    ArrayList salesList;

    ListView listView;
    Database dbSQ;

    AutoCompleteTextView edsales;
    ImageButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbSQ = new Database(MainActivity.this);
        // this.deleteDatabase("sq_db.db");
        SQLiteDatabase sqLiteDatabase = dbSQ.getWritableDatabase();

        tvNoSales = findViewById(R.id.tvNoSales);
        tvNamaSales = findViewById(R.id.tvNamaSales);

        edsales = findViewById(R.id.edsales);
        btnSubmit = findViewById(R.id.btnSubmit);

        edsales.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //get_sales(edsales.getText().toString(),api.key);
            }
            return false;
        });
        edsales.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String) parent.getItemAtPosition(position);
            tvNamaSales.setText(selection);
            //tvNoSales.setText(s);
            //get_sales(edsales.getText().toString(),api.key);
        });

        Cursor cur = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM sales", null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            loadSales();
            if (cur.getInt (0) == 0) {    // Zero count means empty table.
                getSalesAll(sqLiteDatabase);
            }
        }

        //if data tabel sales belum terisi
        //getSalesAll(sqLiteDatabase);
        //else
        //loadSales();

        btnSubmit.setOnClickListener(v -> {
            //get_sales(edsales.getText().toString(),api.key);
        });

    }

    private void loadSales(){

        //pengisian list dari database sqlite
        salesList = dbSQ.fetchData();
        //setting list data ke adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, salesList);
        //setting list data ke autocomplete
        edsales.setAdapter(adapter);

        edsales.setThreshold(1);
        edsales.setTextColor(Color.BLACK);
    }

    private void getSalesAll(SQLiteDatabase sqlite) {
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
                    /*String query = "select * from sales";
                    try (Cursor cursor = sqlite.rawQuery(query, null)) {
                        if(cursor.getCount()>0) {
                            dbSQ.updateData(SaANo, SaNm, SaLsUsr);
                        } else {
                            dbSQ.insertData(SaANo, SaNm, SaLsUsr);
                        }

                    }*/

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG_SALES_ALL", "onResponse: " + e.toString());
            }

            //pengisian list dari database sqlite
            salesList = dbSQ.fetchData();
            //setting list data ke adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, salesList);
            //setting list data ke autocomplete
            edsales.setAdapter(adapter);

            edsales.setThreshold(1);
            edsales.setTextColor(Color.BLACK);

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