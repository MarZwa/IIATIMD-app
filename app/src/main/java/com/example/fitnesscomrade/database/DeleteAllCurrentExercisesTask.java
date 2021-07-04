package com.example.fitnesscomrade.database;

public class DeleteAllCurrentExercisesTask implements Runnable {
    private AppDatabase db;

    public DeleteAllCurrentExercisesTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void run() {
        db.currentExercisesDao().deleteAll();
    }
}
