package com.example.fitnesscomrade.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Set {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int set;
    public int exerciseNr;
    public int exercisesLength;

    public int getSet() {
        return set;
    }

    public int getExerciseNr() {
        return exerciseNr;
    }

    public int getExercisesLength() {
        return exercisesLength;
    }
}
