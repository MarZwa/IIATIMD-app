package com.example.fitnesscomrade.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrentExercisesDao {

    @Query("SELECT * FROM CurrentExercises")
    public List<CurrentExercises> getCurrentExercises();

    @Insert
    void insertCurrentExercise(CurrentExercises... currentExercises);

    @Delete
    void delete(CurrentExercises... currentExercises);

    @Query("DELETE FROM CurrentExercises")
    void deleteAll();

}
