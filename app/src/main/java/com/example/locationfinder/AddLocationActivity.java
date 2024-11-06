package com.example.locationfinder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddLocationActivity extends AppCompatActivity {

    private LocationViewModel locationViewModel;
    private EditText addressEditText, latitudeEditText, longitudeEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        addressEditText = findViewById(R.id.addAddressEditText);
        latitudeEditText = findViewById(R.id.addLatitudeEditText);
        longitudeEditText = findViewById(R.id.addLongitudeEditText);
        saveButton = findViewById(R.id.saveButton);

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        saveButton.setOnClickListener(v -> saveLocation());
    }

    private void saveLocation() {
        String address = addressEditText.getText().toString().trim();
        String latitudeText = latitudeEditText.getText().toString().trim();
        String longitudeText = longitudeEditText.getText().toString().trim();

        if (address.isEmpty() || latitudeText.isEmpty() || longitudeText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(latitudeText);
            longitude = Double.parseDouble(longitudeText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        Location newLocation = new Location(address, latitude, longitude);
        locationViewModel.insert(newLocation);
        Toast.makeText(this, "Location added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
