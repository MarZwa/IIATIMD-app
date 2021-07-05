package com.example.fitnesscomrade.database;

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
        return workouts;
    }
}
