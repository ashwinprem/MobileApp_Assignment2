package com.example.locationfinder;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.util.List;

import androidx.lifecycle.LiveData;

public class LocationViewModel extends AndroidViewModel {
    private final LocationRepository repository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
    }

    public void insert(Location location) { repository.insert(location); }
    public void delete(Location location) { repository.delete(location); }
    public void update(Location location) { repository.update(location); }

    public LiveData<Location> findByAddress(String address) {
        return repository.findByAddress(address);
    }

    public LiveData<List<Location>> getAllLocations() {
        return repository.getAllLocations();
    }

    public LiveData<Location> getLocationById(int locationId) {
        return repository.getLocationById(locationId);
    }

}
