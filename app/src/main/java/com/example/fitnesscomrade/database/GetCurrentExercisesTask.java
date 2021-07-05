package com.example.fitnesscomrade.database;

import java.util.List;
import java.util.concurrent.Callable;

public class GetCurrentExercisesTask implements Callable<List<CurrentExercises>> {

    private AppDatabase db;
    private List<CurrentExercises> exercises;

    public GetCurrentExercisesTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public List<CurrentExercises> call() throws Exception {
        exercises = db.currentExercisesDao().getCurrentExercises();
        return exercises;
    }
}
