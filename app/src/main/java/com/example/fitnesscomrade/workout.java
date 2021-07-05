package com.example.fitnesscomrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.CurrentExercises;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetCurrentExercisesTask;
import com.example.fitnesscomrade.database.GetSetTask;
import com.example.fitnesscomrade.database.SaveSetTask;
import com.example.fitnesscomrade.database.Set;
import com.example.fitnesscomrade.database.Workout;
import com.example.fitnesscomrade.myWorkoutsRecycler.MyWorkoutsAdapter;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link workout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class workout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<CurrentExercises> exercises;
    private int set;
    private int exerciseNr;
    private int exerciseLength;

    public workout() {

    }

    public workout(int set, int exerciseNr) {
        this.set = set;
        this.exerciseNr = exerciseNr;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment workout.
     */
    // TODO: Rename and change types and number of parameters
    public static workout newInstance(String param1, String param2) {
        workout fragment = new workout();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        AppDatabase db = AppDatabase.getDbInstance(getActivity());

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<CurrentExercises>> futureCall = executor.submit(new GetCurrentExercisesTask(db));
        try {
            List<CurrentExercises> result = futureCall.get();
            exercises = result;
            followWorkout(view, db);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void followWorkout(View view, AppDatabase db) {


        TextView textview = view.findViewById(R.id.textview);
        TextView repsview = view.findViewById(R.id.repsview);
        Button buttonNext = view.findViewById(R.id.buttonNext);

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Set> futureCall = executor.submit(new GetSetTask(db));
        try {
            Set result = futureCall.get();
            set = result.getSet();
            exerciseNr = result.getExerciseNr();
            exerciseLength = result.exercisesLength;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textview.setText(exercises.get(exerciseNr).getName());
        repsview.setText("Repetitions: " + exercises.get(exerciseNr).getReps());

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(exerciseLength-1 == exerciseNr && set == 3) {
                    NavController navcontroller = Navigation.findNavController(view);
                    navcontroller.navigate(R.id.navigation_myWorkouts);
                }
                else {
                    new Thread(new SaveSetTask(set, exerciseNr, exerciseLength, db)).start();

                    NavController navcontroller = Navigation.findNavController(view);
                    navcontroller.navigate(R.id.navigation_timer);
                }
            }
        });
    }
}