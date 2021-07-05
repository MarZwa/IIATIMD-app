package com.example.fitnesscomrade;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class workouts_list extends Fragment implements workouts_listAdapter.OnListItemListener  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private JSONArray array;
    private List<String> names = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts_list, container, false);

        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "http://10.0.2.2:8000/api/workouts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray){
                        array = jsonArray;

                        for(int i=0;i<array.length();i++){
                            try {
                                names.add(array.getJSONObject(i).getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nietGewerkt", "error");
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

        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerViewAdapter = new workouts_listAdapter(names, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onListClick(int position) {

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("ID", String.valueOf(position+1)).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Title", names.get(position)).apply();

        NavController navcontroller = Navigation.findNavController(getView());
        navcontroller.navigate(R.id.navigation_workouts_detail);
    }
}
