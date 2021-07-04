package com.example.fitnesscomrade.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SetDao {

    @Query("SELECT * FROM `Set` ORDER BY id DESC LIMIT 1")
    public Set getLastSet();

    @Insert
    void insertSet(Set... set);
}
