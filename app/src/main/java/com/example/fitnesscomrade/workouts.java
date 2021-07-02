package com.example.fitnesscomrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class workouts extends Fragment {

    private EditText email;
    private EditText password;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts, container, false);

        email = v.findViewById(R.id.loginEmail);
        password = v.findViewById(R.id.loginPassword);
        button = v.findViewById(R.id.loginButton);

        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "http://10.0.2.2:8000/api/login";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPassword =  password.getText().toString();

                if(inputEmail.isEmpty() || inputPassword.isEmpty()){

                }

                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    PreferenceManager.getDefaultSharedPreferences(container.getContext()).edit().putString("JWT", response).apply();
                                    PreferenceManager.getDefaultSharedPreferences(container.getContext()).edit().putString("LOGGED_IN", "true").apply();

                                    NavController navcontroller = Navigation.findNavController(v);
                                    navcontroller.navigate(R.id.navigation_workouts_list);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("nietGewerkt", error.getMessage());
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


        return v;
    }
}