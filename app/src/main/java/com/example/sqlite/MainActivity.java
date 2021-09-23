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
    ArrayList<String> salesList;

    Database dbSQ;

    AutoCompleteTextView edsales;
    ImageButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbSQ = new Database(MainActivity.this);

        tvNoSales = findViewById(R.id.tvNoSales);
        tvNamaSales = findViewById(R.id.tvNamaSales);

        edsales = findViewById(R.id.edsales);
        btnSubmit = findViewById(R.id.btnSubmit);

        loadSales();

        btnSubmit.setOnClickListener(v -> {
            //get_sales(edsales.getText().toString(),api.key);
        });

    }

    private void loadSales(){

        salesList = new ArrayList<>();

        //pengisian list dari database sqlite
        salesList = dbSQ.fetchData();

        //setting list data ke adapter
        edsales.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_selectable_list_item, salesList));

        edsales.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //get_sales(edsales.getText().toString(),api.key);
            }
            return false;
        });
        edsales.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String) parent.getItemAtPosition(position);

            tvNamaSales.setText(selection.substring(0, selection.indexOf("-")));
            tvNoSales.setText(selection.substring(selection.lastIndexOf("-")+1));

            Log.d("TAG_SALES_NO", "loadSales: "+selection.substring(selection.lastIndexOf("-")));
        });
    }
}