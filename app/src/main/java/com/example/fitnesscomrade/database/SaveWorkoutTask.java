package com.example.fitnesscomrade.database;

import java.util.ArrayList;

public class SaveWorkoutTask implements Runnable {

    AppDatabase db;

    ArrayList<Exercise> exercises;
    Workout workout;

    public SaveWorkoutTask(AppDatabase db, ArrayList<Exercise> exercises, Workout workout) {
        this.db = db;
        this.exercises = exercises;
        this.workout = workout;
    }

    @Override
    public void run() {
        for(Exercise exercise : exercises) {
            if(!exercise.name.isEmpty() && !exercise.reps.isEmpty()) {
                db.exerciseDao().insertExercise(exercise);
            }
        }

        db.workoutDao().insertWorkout(workout);
    }
}
