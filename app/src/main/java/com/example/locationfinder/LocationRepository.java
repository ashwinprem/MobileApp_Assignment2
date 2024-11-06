package com.example.locationfinder;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationRepository {
    private final LocationDao locationDao;
    private final ExecutorService executorService;

    public LocationRepository(Application application) {
        LocationDatabase db = LocationDatabase.getDatabase(application);
        locationDao = db.locationDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Location location) {
        executorService.execute(() -> locationDao.insert(location));
    }

    public void delete(Location location) {
        executorService.execute(() -> locationDao.delete(location));
    }

    public void update(Location location) {
        executorService.execute(() -> locationDao.update(location));
    }

    public LiveData<Location> findByAddress(String address) {
        return locationDao.findByAddressLiveData(address);
    }

    public LiveData<List<Location>> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public LiveData<Location> getLocationById(int locationId) {
        return locationDao.getLocationById(locationId);
    }

}


