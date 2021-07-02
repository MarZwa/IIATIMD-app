package com.example.fitnesscomrade.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Transaction
    @Query("SELECT * FROM Workout")
    public List<WorkoutWithExercises> getWorkoutsWithExercises();

    @Query("SELECT * FROM Workout")
    List<Workout> getAllWorkoutNames();

    @Query("SELECT name FROM Workout ORDER BY id ASC LIMIT 1")
    public String getFirstWorkoutName();

    @Query("SELECT id FROM Workout ORDER BY id DESC LIMIT 1")
    public int getLastWorkoutId();

    @Insert
    void insertWorkout(Workout... workouts);

    @Delete
    void delete(Workout workout);

}
