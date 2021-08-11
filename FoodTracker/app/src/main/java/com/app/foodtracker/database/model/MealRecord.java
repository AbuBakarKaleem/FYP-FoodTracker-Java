package com.app.foodtracker.database.model;

import static com.app.foodtracker.Utils.Utils.MEAL_COL_DATE;
import static com.app.foodtracker.Utils.Utils.MEAL_COL_DESCRIPTION;
import static com.app.foodtracker.Utils.Utils.MEAL_COL_TIME;
import static com.app.foodtracker.Utils.Utils.MEAL_COL_TYPE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "MealRecord")
public class MealRecord {

    @PrimaryKey(autoGenerate = true) int mealID = 0;
    @ColumnInfo(name = MEAL_COL_TYPE) String mealType;
    @ColumnInfo(name = MEAL_COL_DATE) String mealDate;
    @ColumnInfo(name = MEAL_COL_TIME) String mealTime;
    @ColumnInfo(name = MEAL_COL_DESCRIPTION) String mealDescription;

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }
}
