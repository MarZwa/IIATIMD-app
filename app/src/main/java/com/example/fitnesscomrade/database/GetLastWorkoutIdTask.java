package com.example.fitnesscomrade.database;

import java.util.concurrent.Callable;

public class GetLastWorkoutIdTask implements Callable<Long> {

    private AppDatabase db;

    public GetLastWorkoutIdTask(AppDatabase db) {
         this.db = db;
    }

    @Override
    public Long call() throws Exception {
        long workoutId = db.workoutDao().getLastWorkoutId();
        return workoutId;
    }
}
