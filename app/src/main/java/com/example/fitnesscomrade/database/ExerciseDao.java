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

//    @Query("SELECT * FROM Workout")
//    List<Workout> getAllWorkouts();

    @Insert
    void insertExercise(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);
}
