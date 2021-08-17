package com.app.foodtracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodtracker.Interface.HistoryItemClickListener;
import com.app.foodtracker.R;
import com.app.foodtracker.database.model.MealRecord;

import java.util.ArrayList;

public class MealHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MealHistoryRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MealRecord> mealList;
    private HistoryItemClickListener listener;

    public MealHistoryRecyclerViewAdapter(ArrayList<MealRecord> mealList, HistoryItemClickListener listener) {
        this.mealList = mealList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            MealRecord mealData = mealList.get(position);
            holder.tv_itemMealType.setText(mealData.getMealType());
            holder.tv_itemMealDescription.setText(mealData.getMealDescription());
            holder.tv_itemMealDateTime.setText(mealData.getMealDate() + " " + mealData.getMealTime());
            holder.ll_base.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(mealData);
                }
            });
        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         AppCompatTextView tv_itemMealDateTime;
         AppCompatTextView tv_itemMealType;
         AppCompatTextView  tv_itemMealDescription ;
         LinearLayout ll_base;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemMealType = itemView.findViewById(R.id.tv_itemMealType);
            tv_itemMealDateTime = itemView.findViewById(R.id.tv_itemMealDateTime);
            tv_itemMealDescription = itemView.findViewById(R.id.tv_itemMealDescription);
            ll_base = itemView.findViewById(R.id.ll_base);
        }
    }
}
