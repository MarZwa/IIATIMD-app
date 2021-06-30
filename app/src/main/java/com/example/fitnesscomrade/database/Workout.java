package com.example.fitnesscomrade.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workout {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;
}
