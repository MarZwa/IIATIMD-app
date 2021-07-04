package com.example.fitnesscomrade.myWorkoutsRecycler;

import android.appwidget.AppWidgetProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.R;
import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.CurrentExercises;
import com.example.fitnesscomrade.database.DeleteAllCurrentExercisesTask;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetExercisesOnWorkoutIdTask;
import com.example.fitnesscomrade.database.SaveCurrentExercisesTask;
import com.example.fitnesscomrade.database.SaveSetTask;
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
                new Thread(new DeleteAllCurrentExercisesTask(db)).start();
                Future<List<Exercise>> futureCall = executor.submit(new GetExercisesOnWorkoutIdTask(db, position));
                try {
                    List<Exercise> result = futureCall.get();
                    int exercisesLength = result.size();
                    Log.d("lengte", String.valueOf(exercisesLength));

                    for(Exercise exercise : result) {
                        CurrentExercises currentExercise = new CurrentExercises();
                        currentExercise.name = exercise.getName();
                        currentExercise.reps = exercise.getReps();

                        new Thread(new SaveCurrentExercisesTask(currentExercise, db)).start();
                    }

                    new Thread(new SaveSetTask(1, 0, exercisesLength, db)).start();

                    NavController navcontroller = Navigation.findNavController(v);
                    navcontroller.navigate(R.id.navigation_workout);
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
