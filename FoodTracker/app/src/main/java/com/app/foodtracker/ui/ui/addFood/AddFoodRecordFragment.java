package com.app.foodtracker.ui.ui.addFood;

import static com.app.foodtracker.Utils.Utils.STR_BREAKFAST;
import static com.app.foodtracker.Utils.Utils.STR_DINNER;
import static com.app.foodtracker.Utils.Utils.STR_LUNCH;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.foodtracker.R;
import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.databinding.AddFoodRecordFragmentBinding;
import com.app.foodtracker.databinding.FragmentHomeBinding;
import com.app.foodtracker.session.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFoodRecordFragment extends Fragment {

    private AddFoodRecordViewModel mViewModel;
    private AddFoodRecordFragmentBinding binding;
    private View rootView;
    private String foodType;
    private int foodTypeArg;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AddFoodRecordFragmentBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(AddFoodRecordViewModel.class);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodTypeArg=getArguments().getInt("value");

        setMealTypeOnUI();
        setDateAndTimeToUI();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDateAndTimeToUI() {
        String dateTime = getCurrentDateTime();
        binding.tvSelectDate.setText( dateTime.split(" ")[0]);
        binding.tvSelectTime.setText( dateTime.split(" ")[1] + " " + dateTime.split(" ")[2]);
    }

    private void validation() {
        if (binding.tvSelectedMealType.getText().toString().isEmpty()) {
            Utils.showToast(rootView.getContext(), "Meal Type is Required");
            return;

        }
        if (binding.tvSelectDate.getText().toString().contentEquals(getString(R.string.select_date))) {
            Utils.showToast(rootView.getContext(), "please select date");
            return;

        }
        if (binding.tvSelectTime.getText().toString().contentEquals(getString(R.string.select_time))) {
            Utils.showToast(rootView.getContext(), "please select time");
            return;

        }
        if (binding.etDescription.getText().toString().isEmpty()) {
            binding.etDescription.setError(getString(R.string.required));
            return;

        }
        saveMealInfoIntoDatabase();

    }

    private void saveMealInfoIntoDatabase() {
        try {
            MealRecord mealRecord = new MealRecord(
                    foodType.trim(),
                    binding.tvSelectDate.getText().toString().trim(),
                    binding.tvSelectTime.getText().toString().trim(),
                    binding.etDescription.getText().toString().trim()
            );
            Long recordInsertStatus = mViewModel.insertMealRecord(rootView.getContext(), mealRecord);
            if (recordInsertStatus > 0) {
                showToast(getString(R.string.meal_insert_message));
                setMealTakenValue(foodType.trim());
                goBack();

            } else {
                showToast(getString(R.string.meal_insert_fail_message));
            }

        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
            Utils.showToast(rootView.getContext(), getString(R.string.something_went_wrong));
        }
    }
    private void setMealTakenValue(String value) {
        try {
            SessionManager sessionManager = new SessionManager(rootView.getContext());
            switch (value) {
                case STR_BREAKFAST:{
                    sessionManager.setLastBreakfast(getCurrentDateTime().split(" ")[0]);
                    break;
                }
                case STR_LUNCH : {
                    sessionManager.setLastLunch(getCurrentDateTime().split(" ")[0]);
                    break;
                }
                case STR_DINNER : {
                    sessionManager.setLastDinner(getCurrentDateTime().split(" ")[0]);
                    break;
                }
                default: {
                    return;
                }
            }
        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        }
    }

    @SuppressLint("SetTextI18n")
    private void setMealTypeOnUI() {
        SessionManager sessionManager = new SessionManager(rootView.getContext());

        switch (foodTypeArg) {
            case 0: {
                foodType = STR_BREAKFAST;
                String isBreakfastTaken = sessionManager.getLastBreakFast();
                if (!isBreakfastTaken.isEmpty() && isBreakfastTaken.equals(getCurrentDateTime().split(" ")[0])) {
                    hideAddRecordButton(foodType);
                }
                break;
            }
            case 1: {
                foodType = STR_LUNCH;
                String isLunchTaken = sessionManager.getLastLunch();
                if (!isLunchTaken.isEmpty() && isLunchTaken.equals(getCurrentDateTime().split(" ")[0])
                ) {
                    hideAddRecordButton(foodType);
                }
                break;
            }
            case 2: {
                foodType = STR_DINNER;
                String isDinnerTaken = sessionManager.getLastDinner();
                if (!isDinnerTaken.isEmpty() && isDinnerTaken.equals(getCurrentDateTime().split(" ")[0])
                ) {
                    hideAddRecordButton(foodType);
                }
                break;
            }
            case 3: {
                foodType = Utils.STR_SNACKS;
                break;
            }
            default: {
                foodType = "";
            }
        }
        binding.tvSelectedMealType.setText(getString(R.string.select_meal) + foodType);
    }

    private void goBack() {
        Navigation.findNavController(rootView).navigateUp();
    }

    private void showToast(String message) {
        Utils.showToast(rootView.getContext(), message);
    }

    private void hideAddRecordButton(String value) {
        binding.tvLastMealTaken.setText("Today's " +foodType+" is already done");
        binding.tvLastMealTaken.setVisibility(View.VISIBLE);
        binding.btnSave.setVisibility(View.GONE);
    }

    private String getCurrentDateTime() {
        String currentDateTime = "";
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            currentDateTime = df.format(Calendar.getInstance().getTime());
            Log.e("App", "Current date =========>>$currentDateTime");
        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        }
        return currentDateTime;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}