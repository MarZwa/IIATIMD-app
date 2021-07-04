package com.example.fitnesscomrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.CurrentExercises;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetExercisesOnWorkoutIdTask;
import com.example.fitnesscomrade.database.GetSetTask;
import com.example.fitnesscomrade.database.SaveCurrentExercisesTask;
import com.example.fitnesscomrade.database.SaveSetTask;
import com.example.fitnesscomrade.database.Set;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Timer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Timer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView countdownText;
    private Button countdownButton;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 10000;
    private boolean timerRunning;

    private Set currentSet;
    private int exercisesLength;

    public Timer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Timer.
     */
    // TODO: Rename and change types and number of parameters
    public static Timer newInstance(String param1, String param2) {
        Timer fragment = new Timer();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        AppDatabase db = AppDatabase.getDbInstance(getActivity());

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Set> futureCall = executor.submit(new GetSetTask(db));
        try {
            Set result = futureCall.get();
            currentSet = result;
            exercisesLength = result.exercisesLength;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countdownText = view.findViewById(R.id.countdown_text);
        countdownButton = view.findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();

        return view;
    }

    public void startStop() {
        if(timerRunning) {
            stopTimer();
        }
        else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
        AppDatabase db = AppDatabase.getDbInstance(getActivity());

            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                countdownButton.setText("Next set");
                countdownButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newSet = currentSet.getSet() + 1;
                        int newExerciseNr = currentSet.exerciseNr + 1;

                        if(newSet > 3) {
                            new Thread(new SaveSetTask(1, newExerciseNr, exercisesLength, db)).start();

                            NavController navcontroller = Navigation.findNavController(v);
                            navcontroller.navigate(R.id.navigation_workout);
                        }
                        else {
                            new Thread(new SaveSetTask(newSet, currentSet.exerciseNr, exercisesLength, db)).start();

                            NavController navcontroller = Navigation.findNavController(v);
                            navcontroller.navigate(R.id.navigation_workout);
                        }
                    }
                });
            }
        }.start();

        countdownButton.setText("PAUSE");
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countdownButton.setText("START");
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }
}