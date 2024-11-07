package com.example.locationfinder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();

    private static volatile LocationDatabase INSTANCE;

    public static LocationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LocationDatabase.class, "location_database")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Insert GTA locations when the database is first created
            Executors.newSingleThreadExecutor().execute(() -> {
                LocationDao dao = INSTANCE.locationDao();
                dao.insertAll(getGtaLocations()); // Insert predefined GTA locations
            });
        }

        // TEMPORARY: Clear and populate database each time app runs
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                LocationDao dao = INSTANCE.locationDao();
                dao.deleteAll();  // Clear existing data
                dao.insertAll(getGtaLocations());  // Re-populate with sample data
            });
        }
    };

    // Method to provide a list of GTA cities with their coordinates
    private static Location[] getGtaLocations() {
        return new Location[] {
                new Location("Toronto", 43.65107, -79.347015),
                new Location("Mississauga", 43.589045, -79.644119),
                new Location("Brampton", 43.731548, -79.762417),
                new Location("Markham", 43.8561, -79.337),
                new Location("Vaughan", 43.8361, -79.4983),
                new Location("Richmond Hill", 43.8828, -79.4403),
                new Location("Oakville", 43.4675, -79.6877),
                new Location("Burlington", 43.3255, -79.799),
                new Location("Whitby", 43.8971, -78.9429),
                new Location("Ajax", 43.8509, -79.0204),
                new Location("Pickering", 43.8384, -79.0868),
                new Location("Oshawa", 43.8971, -78.8658),
                new Location("King City", 43.9256, -79.5265),
                new Location("Caledon", 43.8668, -79.9946),
                new Location("Clarington", 43.9367, -78.608),
                new Location("Aurora", 44.0065, -79.4504),
                new Location("Newmarket", 44.0592, -79.4613),
                new Location("Milton", 43.5183, -79.8774),
                new Location("Georgina", 44.2968, -79.4363),
                new Location("East Gwillimbury", 44.1002, -79.4277)
                // Add more cities as needed
        };
    }
}
