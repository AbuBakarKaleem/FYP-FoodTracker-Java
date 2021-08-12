package com.app.foodtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.foodtracker.database.dao.AccessDao;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.database.model.User;

@Database(entities = {User.class, MealRecord.class}, version = 1, exportSchema = true)
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
