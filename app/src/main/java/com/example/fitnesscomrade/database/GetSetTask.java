package com.example.fitnesscomrade.database;

import android.telecom.Call;

import java.util.concurrent.Callable;

public class GetSetTask implements Callable<Set> {

    private AppDatabase db;

    public GetSetTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Set call() throws Exception {
        return db.setDao().getLastSet();
    }
}
