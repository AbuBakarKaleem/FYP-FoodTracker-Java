package com.app.foodtracker.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.foodtracker.database.dao.AccessDao;

public abstract  class DatabaseInstance extends RoomDatabase {

    public abstract AccessDao accessDao();

    private static DatabaseInstance INSTANCE;

    public static DatabaseInstance getDatabaseClient(Context context) {

        if (INSTANCE != null) return INSTANCE;

        synchronized(context) {

            INSTANCE = Room
                    .databaseBuilder(context, DatabaseInstance.class, "FOOD TRACKER")
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();

            return INSTANCE;

        }
    }
}
