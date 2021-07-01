package com.example.fitnesscomrade.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "workout_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
