package com.example.fitnesscomrade.myWorkoutsRecycler;

import android.appwidget.AppWidgetProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.R;
import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetExercisesOnWorkoutIdTask;
import com.example.fitnesscomrade.database.Workout;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyWorkoutsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Workout> workouts;
    private AppDatabase db;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public MyWorkoutsAdapter(List<Workout> workouts, AppDatabase db) {
        this.workouts = workouts;
        this.db = db;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.fragment_my_workouts;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(String.valueOf(workouts.get(position).getName()));
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Position: ", String.valueOf(position+1));
                Future<List<Exercise>> futureCall = executor.submit(new GetExercisesOnWorkoutIdTask(db, position));
                try {
                    List<Exercise> result = futureCall.get();
                    Log.d("Exercises length", String.valueOf(result.size()));
                    Log.d("Exercise name", result.get(0).getName());
                    Log.d("Exercise reps", result.get(0).getReps());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}
