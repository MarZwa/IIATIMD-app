package com.example.fitnesscomrade.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long workoutId;
    public String name;
    public String reps;

    public String getName() {
        return name;
    }

    public String getReps() {
        return reps;
    }
}
