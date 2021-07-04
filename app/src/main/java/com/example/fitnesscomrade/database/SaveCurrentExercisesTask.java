package com.example.fitnesscomrade.database;

import android.util.Log;

public class SaveCurrentExercisesTask implements Runnable {

    private AppDatabase db;
    private CurrentExercises currentExercise;

    public SaveCurrentExercisesTask(CurrentExercises currentExercise, AppDatabase db) {
        this.currentExercise = currentExercise;
        this.db = db;
    }

    @Override
    public void run() {
        db.currentExercisesDao().insertCurrentExercise(currentExercise);
    }
}
