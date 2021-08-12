package com.app.foodtracker.ui.ui.history;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.foodtracker.Interface.HistoryItemClickListener;
import com.app.foodtracker.R;
import com.app.foodtracker.adapters.MealHistoryRecyclerViewAdapter;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.databinding.FragmentHomeBinding;
import com.app.foodtracker.databinding.HistoryFragmentBinding;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements HistoryItemClickListener {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;
    private View rootView;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init(){
        try {
            mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
            progressDialog = new ProgressDialog(rootView.getContext());
            progressDialog.setMessage("Processing..");
            ArrayList<MealRecord> historyList = (ArrayList<MealRecord>) mViewModel.getMealRecords(rootView.getContext());
            if (historyList.size() > 0) {
                populateRecyclerView(historyList);

            } else {
                hideShareButtonAndRecyclerView();
            }
        }catch (Exception e){
            Log.e("App","Error "+ e.getMessage().toString());
        }


    }
    private void populateRecyclerView(ArrayList<MealRecord> list){

        LinearLayoutManager manager = new LinearLayoutManager(rootView.getContext());
        binding.rcMealHistory.setLayoutManager(manager);
        binding.rcMealHistory.setHasFixedSize(true);

        MealHistoryRecyclerViewAdapter mealListAdapter = new MealHistoryRecyclerViewAdapter(list, this);
        binding.rcMealHistory.setAdapter(mealListAdapter);

    }
    private void hideShareButtonAndRecyclerView() {
       binding.rcMealHistory.setVisibility(View.GONE);
       binding.tvNoDataFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }

    @Override
    public void onItemClickListener(MealRecord mealRecord) {

    }

}