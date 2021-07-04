package com.example.fitnesscomrade.database;

public class SaveSetTask implements Runnable {

    private int set;
    private int exerciseNr;
    private int exercisesLength;
    private AppDatabase db;

    public SaveSetTask(int set, int exerciseNr, int exercisesLength, AppDatabase db) {
        this.set = set;
        this.exerciseNr = exerciseNr;
        this.exercisesLength = exercisesLength;
        this.db = db;
    }

    @Override
    public void run() {
        Set newSet = new Set();
        newSet.set = set;
        newSet.exerciseNr = exerciseNr;
        newSet.exercisesLength = exercisesLength;

        db.setDao().insertSet(newSet);
    }
}
