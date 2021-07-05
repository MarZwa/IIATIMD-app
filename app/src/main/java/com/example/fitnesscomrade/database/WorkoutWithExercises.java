package com.example.fitnesscomrade.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class WorkoutWithExercises {
    @Embedded public Workout workout;
    @Relation(
            parentColumn = "id",
            entityColumn = "workoutId"
    )
    public List<Exercise> exercises;
}
