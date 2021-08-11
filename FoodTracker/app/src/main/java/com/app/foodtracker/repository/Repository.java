package com.app.foodtracker.repository;

import android.content.Context;

import com.app.foodtracker.database.DatabaseInstance;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.database.model.User;

import java.util.List;

public class Repository {

    public static DatabaseInstance databaseInstance;

    public static DatabaseInstance initializeDB(Context context) {
        return DatabaseInstance.getDatabaseClient(context);
    }
    public static Long registerUser(Context context, User user) {

        databaseInstance = initializeDB(context);
        return databaseInstance.accessDao().registerUser(user);
    }
    public static User loginUser(Context context, String email, String password) {
        databaseInstance = initializeDB(context);
        return databaseInstance.accessDao().loginUser(email, password);
    }

    public static Long insertMealRecord(Context context, MealRecord mealRecord){
        databaseInstance = initializeDB(context);
        return databaseInstance.accessDao().insertMealRecord(mealRecord);
    }
    public static List<MealRecord> getMealRecords(Context context) {
        databaseInstance = initializeDB(context);
        return databaseInstance.accessDao().getAllMealRecords();

    }
}
