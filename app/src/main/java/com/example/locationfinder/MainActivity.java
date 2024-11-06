package com.example.locationfinder;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private LocationViewModel locationViewModel;
    private LocationAdapter locationAdapter;
    private List<Location> allLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        locationAdapter = new LocationAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.locationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);

        EditText searchBar = findViewById(R.id.searchBar);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
            startActivity(intent);
        });

        locationViewModel.getAllLocations().observe(this, locations -> {
            allLocations = locations;
            locationAdapter.setLocationList(locations);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Location> filteredList = allLocations.stream()
                        .filter(location -> location.getAddress().toLowerCase().contains(s.toString().toLowerCase()))
                        .collect(Collectors.toList());
                locationAdapter.setLocationList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}

