package com.app.foodtracker.ui.ui.addFood;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.repository.Repository;

public class AddFoodRecordViewModel extends ViewModel {
    Long insertMealRecord(Context context, MealRecord mealRecord){
        return Repository.insertMealRecord(context,mealRecord);
    }
}