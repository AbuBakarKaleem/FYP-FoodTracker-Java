package com.app.foodtracker.ui.ui.addFood;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.foodtracker.R;
import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.databinding.AddFoodRecordFragmentBinding;
import com.app.foodtracker.databinding.FragmentHomeBinding;

public class AddFoodRecordFragment extends Fragment {

    private AddFoodRecordViewModel mViewModel;
    private AddFoodRecordFragmentBinding binding;
    private View rootView;
    private String foodType;

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
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
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
            binding.etDescription.setError( getString(R.string.required));
            return;

        }
        saveMealInfoIntoDatabase();

    }
    private void saveMealInfoIntoDatabase() {
        try {
            MealRecord mealRecord =  new MealRecord(
                    foodType.trim(),
                    binding.tvSelectDate.getText().toString().trim(),
                    binding.tvSelectTime.getText().toString().trim(),
                    binding.etDescription.getText().toString().trim()
            );
           /* val recordInsertStatus = mViewModel.insertMealRecord(rootView.getContext(), mealRecord);
            if (recordInsertStatus > 0) {
                showToast(getString(R.string.meal_insert_message))
                setMealTakenValue(foodType.trim())
                goBack()

            } else {
                showToast(getString(R.string.meal_insert_fail_message))
            }*/

        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
            Utils.showToast(rootView.getContext(), getString(R.string.something_went_wrong))
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}