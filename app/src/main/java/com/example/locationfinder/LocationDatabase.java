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
                            .addCallback(roomCallback) // Add this line
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
            // Populate the database in the background
            Executors.newSingleThreadExecutor().execute(() -> {
                LocationDao dao = INSTANCE.locationDao();
                // Insert sample data
                dao.insertAll(getSampleLocations());
            });
        }
    };

    // Method to provide 100 sample locations
    private static Location[] getSampleLocations() {
        return new Location[] {
                new Location("Toronto", 43.65107, -79.347015),
                new Location("Mississauga", 43.5890452, -79.6441198),
                new Location("Brampton", 43.7315479, -79.7624177),
                new Location("Markham", 43.8561, -79.337),
                new Location("Vaughan", 43.8361, -79.4983),
                // Add more locations here up to 100
        };
    }
}


