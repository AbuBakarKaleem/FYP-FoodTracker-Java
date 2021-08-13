package com.app.foodtracker.ui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.app.foodtracker.R;
import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }
    private void init(){
        binding.cvBreakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddFoodRecord(Utils.BREAKFAST);
            }
        });
        binding.cvLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddFoodRecord(Utils.LUNCH);
            }
        });
        binding.cvDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddFoodRecord(Utils.DINNER);
            }
        }) ;
        binding.cvSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddFoodRecord(Utils.SNACKS);
            }
        });
    }
    private void moveToAddFoodRecord(int value){
        Bundle bundle = new Bundle();
        bundle.putInt("value", value);
        Navigation.findNavController(root).navigate(R.id.toAddfood,bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}