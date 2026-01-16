package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    EditText inputCity;
    Button btnAdd, btnDelete, btnConfirm;

    int selectedIndex = -1;
    View selectedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        inputCity = findViewById(R.id.input_city);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnConfirm = findViewById(R.id.btn_confirm);


        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));


        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);


        inputCity.setVisibility(View.GONE);
        btnConfirm.setVisibility(View.GONE);


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;

            if (selectedView != null) selectedView.setBackgroundColor(0x00000000);
            selectedView = view;
            selectedView.setBackgroundColor(0xFFE0E0E0);
        });


        btnAdd.setOnClickListener(v -> {
            inputCity.setText("");
            inputCity.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
            inputCity.requestFocus();
            Toast.makeText(this, "Type a city name then press CONFIRM", Toast.LENGTH_SHORT).show();
        });


        btnConfirm.setOnClickListener(v -> {
            String newCity = inputCity.getText().toString().trim();

            if (newCity.isEmpty()) {
                Toast.makeText(this, "Please type a city name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dataList.contains(newCity)) {
                Toast.makeText(this, "City already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();

            inputCity.setText("");
            inputCity.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        });


        btnDelete.setOnClickListener(v -> {
            if (selectedIndex == -1) {
                Toast.makeText(this, "Tap a city to select it first", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();


            selectedIndex = -1;
            if (selectedView != null) {
                selectedView.setBackgroundColor(0x00000000);
                selectedView = null;
            }
        });
    }
}
