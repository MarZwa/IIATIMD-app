package com.example.fitnesscomrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Workout;

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
        Button saveButton = v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewUser(nameInput.getText().toString());
            }
        });
        return v;
    }

    private void saveNewUser(String name) {
        AppDatabase db = AppDatabase.getDbInstance(getActivity());

        Workout workout = new Workout();
        workout.name = name;
        db.workoutDao().insertWorkout(workout);
    }
}