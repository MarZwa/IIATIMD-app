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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class workouts_detail extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<String> exercises = new ArrayList<String>();
    private JSONArray array;
    private TextView title;
    private Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts_detail, container, false);

        button = v.findViewById(R.id.addWorkoutBtn);
        title = v.findViewById(R.id.workoutTitle);
        title.setText(PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("Title", "Workout"));

        String id = PreferenceManager.getDefaultSharedPreferences(container.getContext()).getString("ID", "defaultStringIfNothingFound");
        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "http://10.0.2.2:8000/api/workout/"+ id +"/exercises";

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
