package com.example.fitnesscomrade.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class, Exercise.class, CurrentExercises.class, Set.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract CurrentExercisesDao currentExercisesDao();
    public abstract SetDao setDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "workout_database")
                       .fallbackToDestructiveMigration()
                       .build();
        }
        return INSTANCE;
    }
}
