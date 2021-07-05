package com.example.fitnesscomrade;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnesscomrade.database.AppDatabase;
import com.example.fitnesscomrade.database.Exercise;
import com.example.fitnesscomrade.database.GetLastWorkoutIdTask;
import com.example.fitnesscomrade.database.SaveWorkoutTask;
import com.example.fitnesscomrade.database.Workout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class workouts_detail extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<String> exercises = new ArrayList<String>();
    private JSONArray array;
    private TextView title;
    private Button button;
    private long workoutId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts_detail, container, false);

        button = v.findViewById(R.id.addWorkoutBtn);
        title = v.findViewById(R.id.workoutTitle);
        title.setText(PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("Title", "Workout"));

        String id = PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("ID", "defaultStringIfNothingFound");
        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "https://mysterious-waters-19165.herokuapp.com/api/workout/"+ id +"/exercises";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0;i<response.length();i++){
                            try {
                                exercises.add(response.getJSONObject(i).getString("name") + ": 3 sets of " + response.getJSONObject(i).getString("reps") + " reps with 60 seconds rest between sets");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        recyclerViewAdapter.notifyDataSetChanged();
                        array = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nietGewerkt", error.getMessage());
                    }
                })
                {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("JWT", "defaultStringIfNothingFound"));
                return headerMap;
                }
            };
        queue.add(jsonArrayRequest);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getActivity());
                ArrayList<Exercise> exercises = new ArrayList<>();

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
                workout.name = PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("Title", "Workout");

                for(int i=0;i<array.length();i++){
                    try {
                        Exercise exercise = new Exercise();
                        exercise.workoutId = workoutId + 1;
                        exercise.name = array.getJSONObject(i).getString("name");
                        exercise.reps = array.getJSONObject(i).getString("reps");

                        exercises.add(exercise);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                new Thread(new SaveWorkoutTask(db, exercises, workout)).start();
            }
        });

        recyclerView = v.findViewById(R.id.wdRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerViewAdapter = new workouts_detailAdapter(exercises);
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }
}
