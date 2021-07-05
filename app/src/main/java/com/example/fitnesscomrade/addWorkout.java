package com.example.fitnesscomrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.CurrentExercises;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetCurrentExercisesTask;
import com.example.fitnesscomrade.database.GetLastWorkoutIdTask;
import com.example.fitnesscomrade.database.SaveWorkoutTask;
import com.example.fitnesscomrade.database.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addWorkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addWorkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private long workoutId;

    public addWorkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addWorkout.
     */
    // TODO: Rename and change types and number of parameters
    public static addWorkout newInstance(String param1, String param2) {
        addWorkout fragment = new addWorkout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_workout, container, false);

        final EditText nameInput = v.findViewById(R.id.nameInput);

        final EditText exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        final EditText repsInput = v.findViewById(R.id.repetitions);
        final EditText exerciseNameInput2 = v.findViewById(R.id.exerciseNameInput2);
        final EditText repsInput2 = v.findViewById(R.id.repetitions2);
        final EditText exerciseNameInput3 = v.findViewById(R.id.exerciseNameInput3);
        final EditText repsInput3 = v.findViewById(R.id.repetitions3);

        ArrayList<EditText> inputs = new ArrayList<EditText>();
        inputs.add(nameInput);
        inputs.add(exerciseNameInput);
        inputs.add(repsInput);
        inputs.add(exerciseNameInput2);
        inputs.add(repsInput2);
        inputs.add(exerciseNameInput3);
        inputs.add(repsInput3);

        Button saveButton = v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewWorkout(nameInput.getText().toString(),
                               exerciseNameInput.getText().toString(),
                               repsInput.getText().toString(),
                               exerciseNameInput2.getText().toString(),
                               repsInput2.getText().toString(),
                               exerciseNameInput3.getText().toString(),
                               repsInput3.getText().toString());

                for(EditText input : inputs) {
                    input.setText(null);
                }
            }
        });
        return v;
    }

    private void saveNewWorkout(String name, String exName, String reps, String exName2, String reps2, String exName3, String reps3) {
        AppDatabase db = AppDatabase.getDbInstance(getActivity());

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Long> futureCall = executor.submit(new GetLastWorkoutIdTask(db));
        try {
            long result = futureCall.get();
            workoutId = result;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Workout workout = new Workout();
        workout.name = name;

        Exercise exercise1 = new Exercise();
        exercise1.workoutId = workoutId + 1;
        exercise1.name = exName;
        exercise1.reps = reps;

        Exercise exercise2 = new Exercise();
        exercise2.workoutId = workoutId + 1;
        exercise2.name = exName2;
        exercise2.reps = reps2;

        Exercise exercise3 = new Exercise();
        exercise3.workoutId = workoutId + 1;
        exercise3.name = exName3;
        exercise3.reps = reps3;

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(exercise1);
        exercises.add(exercise2);
        exercises.add(exercise3);

        new Thread(new SaveWorkoutTask(db, exercises, workout)).start();
    }
}