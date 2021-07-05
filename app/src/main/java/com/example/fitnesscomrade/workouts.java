package com.example.fitnesscomrade;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class workouts extends Fragment {

    private EditText email;
    private EditText password;
    private Button button;
    private TextView registerLink;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts, container, false);

        email = v.findViewById(R.id.loginEmail);
        password = v.findViewById(R.id.loginPassword);
        button = v.findViewById(R.id.loginButton);
        registerLink = v.findViewById(R.id.registerLink);
        title = v.findViewById(R.id.loginTitle);

        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "https://mysterious-waters-19165.herokuapp.com/api/login";


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPassword =  password.getText().toString();

                if(inputEmail.isEmpty() || inputPassword.isEmpty()){
                    title.setText("No fields can be empty");
                    title.setTextColor(Color.RED);
                    title.setX(240);
                }

                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(!response.isEmpty() ){
                                        PreferenceManager.getDefaultSharedPreferences(container.getContext()).edit().putString("JWT", response).apply();

                                        NavController navcontroller = Navigation.findNavController(v);
                                        navcontroller.navigate(R.id.navigation_workouts_list);
                                    }
                                    else{
                                        title.setText("No account found");
                                        title.setTextColor(Color.RED);
                                        title.setX(230);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("nietGewerkt", "error");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", inputEmail);
                            params.put("password", inputPassword);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavController navcontroller = Navigation.findNavController(v);
                navcontroller.navigate(R.id.navigation_workouts_register);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWT", "defaultStringIfNothingFound") == "defaultStringIfNothingFound")){
            Log.d("Dikke", PreferenceManager.getDefaultSharedPreferences(getContext()).getString("JWT", "defaultStringIfNothingFound"));
            NavController navcontroller = Navigation.findNavController(getView());
            navcontroller.navigate(R.id.navigation_workouts_list);
        }
    }

}