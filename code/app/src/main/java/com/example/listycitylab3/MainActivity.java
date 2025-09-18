package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener, EditCityFragment.EditCityDialogListener {

    private int editPos = -1;

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city) {

            dataList.set(editPos, city);
            cityAdapter.notifyDataSetChanged();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            editPos = position; // Add this line
            City selectedCity = dataList.get(position);

            Bundle args = new Bundle();
            args.putString("cityName", selectedCity.getName());
            args.putString("provinceName", selectedCity.getProvince());

            EditCityFragment editFragment = new EditCityFragment();
            editFragment.setArguments(args);
            editFragment.show(getSupportFragmentManager(), "Edit City");
        });
    }

}