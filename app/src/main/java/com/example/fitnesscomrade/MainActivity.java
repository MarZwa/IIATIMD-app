package com.example.fitnesscomrade;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Workout;

import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private WorkoutListAdapter workoutListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewWorkout = findViewById(R.id.addNewWorkoutButton);
        addNewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNewWorkoutActivity.class), 100);
            }
        });

        BottomNavigationView navView = findViewById(R.id.bottomNav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_myWorkouts, R.id.navigation_workouts, R.id.navigation_addWorkout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        initRecyclerView();
        loadWorkoutList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        workoutListAdapter = new WorkoutListAdapter(this);
        recyclerView.setAdapter(workoutListAdapter);
    }

    private void loadWorkoutList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Workout> workoutList = db.workoutDao().getAllWorkouts();
        workoutListAdapter.setWorkoutList(workoutList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadWorkoutList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}