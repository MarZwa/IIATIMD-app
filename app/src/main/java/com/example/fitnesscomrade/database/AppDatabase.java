package com.example.fitnesscomrade.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class, Exercise.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "workout_database")
                       .allowMainThreadQueries()
                       .fallbackToDestructiveMigration()
                       .build();
        }
        return INSTANCE;
    }
}
