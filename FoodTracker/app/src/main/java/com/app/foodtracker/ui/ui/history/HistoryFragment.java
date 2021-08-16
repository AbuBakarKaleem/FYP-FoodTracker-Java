package com.app.foodtracker.ui.ui.history;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.foodtracker.Interface.HistoryItemClickListener;
import com.app.foodtracker.R;
import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.adapters.MealHistoryRecyclerViewAdapter;
import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.databinding.FragmentHomeBinding;
import com.app.foodtracker.databinding.HistoryFragmentBinding;
import com.app.foodtracker.session.SessionManager;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryFragment extends Fragment implements HistoryItemClickListener {

    private HistoryViewModel mViewModel;
    private HistoryFragmentBinding binding;
    private View rootView;
    private ProgressDialog progressDialog;
    private ArrayList<MealRecord> mainHistoryList;

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
        listener();

    }

    private void init() {
        try {
            mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
            progressDialog = new ProgressDialog(rootView.getContext());
            progressDialog.setMessage("Processing..");
            mainHistoryList = (ArrayList<MealRecord>) mViewModel.getMealRecords(rootView.getContext());
            if (mainHistoryList.size() > 0) {
                populateRecyclerView(mainHistoryList);

            } else {
                hideShareButtonAndRecyclerView();
            }
        } catch (Exception e) {
            Log.e("App", "Error " + e.getMessage().toString());
        }


    }

    private void listener() {
        binding.checkboxShareAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mainHistoryList.size() > 0) {
                        listToCSV(mainHistoryList);
                    }
                }

            }
        });
    }

    private void populateRecyclerView(ArrayList<MealRecord> list) {

        LinearLayoutManager manager = new LinearLayoutManager(rootView.getContext());
        binding.rcMealHistory.setLayoutManager(manager);
        binding.rcMealHistory.setHasFixedSize(true);

        MealHistoryRecyclerViewAdapter mealListAdapter = new MealHistoryRecyclerViewAdapter(list, this);
        binding.rcMealHistory.setAdapter(mealListAdapter);

    }

    private void hideShareButtonAndRecyclerView() {
        binding.rcMealHistory.setVisibility(View.GONE);
        binding.checkboxShareAll.setVisibility(View.GONE);
        binding.tvNoDataFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onItemClickListener(MealRecord mealRecord) {
        try {
            if (mealRecord != null) {
                ArrayList historyList = new ArrayList<MealRecord>();
                historyList.add(mealRecord);
                listToCSV(historyList);
            } else {
                Utils.showToast(rootView.getContext(), getString(R.string.something_went_wrong));
            }
        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        }
    }

    private void listToCSV(ArrayList<MealRecord> historyList) {
        try {
            if (historyList.size() > 0) {
                String csvFilePath =
                        getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                                .toString() + "/FoodHistory.csv"; // Here csv file name is MyCsvFile.csv
                StringBuilder stringBuilder = new StringBuilder();
                ArrayList<String[]> finalList = new ArrayList<>();
                String[] strValue = new String[1];
                for (int i = 0; i < historyList.size(); i++) {

                    stringBuilder.append(historyList.get(i).getMealType());
                    stringBuilder.append(",");
                    stringBuilder.append(historyList.get(i).getMealDate() + " " + historyList.get(i).getMealTime());
                    stringBuilder.append(",");
                    stringBuilder.append(historyList.get(i).getMealDescription());
                    stringBuilder.append("\n");

                    strValue[0] = stringBuilder.toString();
                    finalList.add(strValue);
                }
                CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
                writer.writeAll(finalList); // data is adding to csv
                writer.close();
                shareFile(csvFilePath);
            }

        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        } finally {
            hideProdressBar();
        }
    }

    private void shareFile(String createdFilePath) {
        try {
            File fileWithinMyDir = new File(createdFilePath);
            if (fileWithinMyDir.exists()) {
                SessionManager sessionManager = new SessionManager(rootView.getContext());
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);

                Uri uriProvider = FileProvider.getUriForFile(
                        rootView.getContext(), rootView.getContext().getApplicationContext().getPackageName() + ".provider",
                        fileWithinMyDir
                );

                intentShareFile.setType("application/csv");
                intentShareFile.putExtra(Intent.EXTRA_STREAM, uriProvider);
                intentShareFile.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Sharing File..."
                );
                String emailBody = "Food History of " + sessionManager.getUserDetails().getFirstName().toString()
                        + " " +
                        sessionManager.getUserDetails().getLastName().toString();

                intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentShareFile.putExtra(Intent.EXTRA_TEXT, emailBody);
                startActivity(Intent.createChooser(intentShareFile, "Share File"));
            } else {
                Utils.showToast(rootView.getContext(), "File not Exist");
            }
        } catch (Exception e) {
            Log.e("App", e.getMessage().toString());
        } finally {
            hideProdressBar();
            binding.checkboxShareAll.setChecked(false);
        }
    }

    private void hideProdressBar() {
        progressDialog.dismiss();
    }
}