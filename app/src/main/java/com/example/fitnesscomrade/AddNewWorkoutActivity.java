package com.example.fitnesscomrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Workout;

public class AddNewWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);

        final EditText nameInput = findViewById(R.id.nameInput);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewUser(nameInput.getText().toString());
            }
        });
    }

    private void saveNewUser(String name) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        Workout workout = new Workout();
        workout.name = name;
        db.workoutDao().insertWorkout(workout);

        finish();
    }
}
