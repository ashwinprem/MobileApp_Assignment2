package com.example.locationfinder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class DetailActivity extends AppCompatActivity {

    private LocationViewModel locationViewModel;
    private Location currentLocation;
    private TextView addressTextView;
    private EditText latitudeEditText, longitudeEditText;
    private Button updateButton, deleteButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addressTextView = findViewById(R.id.addressTextView);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        backButton = findViewById(R.id.backButton);

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        // Retrieve the location ID from the Intent
        int locationId = getIntent().getIntExtra("locationId", -1);

        if (locationId != -1) {
            // Use the ID to find the location in the database
            locationViewModel.getLocationById(locationId).observe(this, location -> {
                if (location != null) {
                    currentLocation = location;
                    addressTextView.setText(location.getAddress());
                    latitudeEditText.setText(String.valueOf(location.getLatitude()));
                    longitudeEditText.setText(String.valueOf(location.getLongitude()));
                } else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Invalid location ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        updateButton.setOnClickListener(v -> updateLocation());
        deleteButton.setOnClickListener(v -> deleteLocation());
        backButton.setOnClickListener(v -> saveAndGoBack()); // Set up back button click listener
    }

    private void updateLocation() {
        if (currentLocation != null) {
            double newLatitude = Double.parseDouble(latitudeEditText.getText().toString());
            double newLongitude = Double.parseDouble(longitudeEditText.getText().toString());

            currentLocation.setLatitude(newLatitude);
            currentLocation.setLongitude(newLongitude);

            locationViewModel.update(currentLocation);
            Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLocation() {
        if (currentLocation != null) {
            locationViewModel.delete(currentLocation);
            Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveAndGoBack() {
        // Save any changes to the location and navigate back to the main screen
        if (currentLocation != null) {
            double newLatitude = Double.parseDouble(latitudeEditText.getText().toString());
            double newLongitude = Double.parseDouble(longitudeEditText.getText().toString());

            currentLocation.setLatitude(newLatitude);
            currentLocation.setLongitude(newLongitude);

            locationViewModel.update(currentLocation); // Update in the database
            Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
        }
        finish(); // Go back to the main screen
    }
}
