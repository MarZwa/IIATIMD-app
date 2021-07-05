package com.example.fitnesscomrade.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    public List<Exercise> getExercises();

    @Query("SELECT * FROM Exercise WHERE workoutId = :input")
    List<Exercise> getExerciseOnWorkoutId(int input);

    @Insert
    void insertExercise(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);
}