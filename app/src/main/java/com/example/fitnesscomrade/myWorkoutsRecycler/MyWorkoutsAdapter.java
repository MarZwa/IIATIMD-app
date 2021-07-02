package com.example.fitnesscomrade.myWorkoutsRecycler;

import android.appwidget.AppWidgetProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.R;
import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Workout;

import java.util.List;
import java.util.Random;

public class MyWorkoutsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Workout> workouts;

    public MyWorkoutsAdapter(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.fragment_my_workouts;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(String.valueOf(workouts.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}
