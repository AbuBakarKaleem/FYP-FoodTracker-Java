package com.app.foodtracker.ui.ui.history;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.repository.Repository;

import java.util.List;

public class HistoryViewModel extends ViewModel {

    public List<MealRecord> getMealRecords(Context context){
        return Repository.getMealRecords(context);
    }
}