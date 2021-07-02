package com.example.fitnesscomrade.database;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.myWorkoutsRecycler.MyWorkoutsAdapter;

import java.util.List;
import java.util.concurrent.Callable;

public class GetMyWorkoutsTask implements Callable<List<Workout>> {

    AppDatabase db;

    public GetMyWorkoutsTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public List<Workout> call() throws Exception {
        List<Workout> workouts = db.workoutDao().getAllWorkoutNames();
        Log.d("tesje", workouts.get(1).getName());
        return workouts;
    }
}
