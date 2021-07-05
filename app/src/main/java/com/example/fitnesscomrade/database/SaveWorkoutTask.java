package com.example.fitnesscomrade.database;

import java.util.ArrayList;

public class SaveWorkoutTask implements Runnable {

    AppDatabase db;

    String name;
    String exName;
    String reps;
    String exName2;
    String reps2;
    String exName3;
    String reps3;

    public SaveWorkoutTask(AppDatabase db, String name, String exName, String reps, String exName2, String reps2, String exName3, String reps3) {
        this.db = db;
        this.name = name;
        this.exName = exName;
        this.reps = reps;
        this.exName2 = exName2;
        this.reps2 = reps2;
        this.exName3 = exName3;
        this.reps3 = reps3;
    }

    @Override
    public void run() {
        long workoutId = db.workoutDao().getLastWorkoutId();

        Workout workout = new Workout();
        workout.name = name;

        Exercise exercise1 = new Exercise();
        exercise1.workoutId = workoutId + 1;
        exercise1.name = exName;
        exercise1.reps = reps;

        Exercise exercise2 = new Exercise();
        exercise2.workoutId = workoutId + 1;
        exercise2.name = exName2;
        exercise2.reps = reps2;

        Exercise exercise3 = new Exercise();
        exercise3.workoutId = workoutId + 1;
        exercise3.name = exName3;
        exercise3.reps = reps3;

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(exercise1);
        exercises.add(exercise2);
        exercises.add(exercise3);

        for(Exercise exercise : exercises) {
            if(!exercise.name.isEmpty() && !exercise.reps.isEmpty()) {
                db.exerciseDao().insertExercise(exercise);
            }
        }

        db.workoutDao().insertWorkout(workout);
    }
}
