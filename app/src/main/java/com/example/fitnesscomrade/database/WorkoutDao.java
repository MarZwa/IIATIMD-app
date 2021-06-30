package com.example.fitnesscomrade.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workout")
    List<Workout> getAllWorkouts();

    @Insert
    void insertWorkout(Workout... workouts);

    @Delete
    void delete(Workout workout);
}
