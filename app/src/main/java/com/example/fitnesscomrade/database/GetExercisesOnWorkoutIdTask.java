package com.example.fitnesscomrade.database;

import java.util.List;
import java.util.concurrent.Callable;

public class GetExercisesOnWorkoutIdTask implements Callable<List<Exercise>> {
    AppDatabase db;
    int workoutId;

    public GetExercisesOnWorkoutIdTask(AppDatabase db, int position) {
        this.workoutId = position+1;
        this.db = db;
    }

    @Override
    public List<Exercise> call() throws Exception {
        List<Exercise> exercises = db.exerciseDao().getExerciseOnWorkoutId(workoutId);
        return exercises;
    }
}
