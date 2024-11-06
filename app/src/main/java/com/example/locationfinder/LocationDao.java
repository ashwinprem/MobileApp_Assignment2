package com.example.locationfinder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insert(Location location);

    @Delete
    void delete(Location location);

    @Update
    void update(Location location);

    @Query("SELECT * FROM location_table WHERE address LIKE :address LIMIT 1")
    LiveData<Location> findByAddressLiveData(String address);

    @Query("SELECT * FROM location_table")
    LiveData<List<Location>> getAllLocations();

    @Insert
    void insertAll(Location... locations);

    @Query("SELECT * FROM location_table WHERE id = :locationId")
    LiveData<Location> getLocationById(int locationId);

}


