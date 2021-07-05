package com.example.fitnesscomrade;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class workouts_register extends Fragment {

    private EditText email;
    private EditText password;
    private EditText name;
    private Button button;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workouts_register, container, false);

        name = v.findViewById(R.id.registerName);
        email = v.findViewById(R.id.registerEmail);
        password = v.findViewById(R.id.registerPassword);
        button = v.findViewById(R.id.registerButton);
        title = v.findViewById(R.id.registerTitle);

        RequestQueue queue = Volley.newRequestQueue(container.getContext());
        final String URL = "https://mysterious-waters-19165.herokuapp.com/api/register";

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPassword =  password.getText().toString();
                String inputName = name.getText().toString();

                if(inputEmail.isEmpty() || inputPassword.isEmpty() || inputName.isEmpty()){
                    title.setText("No fields can be empty");
                    title.setTextColor(Color.RED);
                    title.setX(220);
                }

                else if(!isEmailValid(inputEmail)){
                    title.setText("Fields not correctly filled in");
                    title.setTextColor(Color.RED);
                    title.setX(230);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>(){

                                @Override
                                public void onResponse(String response) {
                                    if(Integer.parseInt(response) == 200){
                                        NavController navcontroller = Navigation.findNavController(v);
                                        navcontroller.navigate(R.id.navigation_workouts);
                                    }
                                    else{
                                        title.setText("Something went wrong");
                                        title.setTextColor(Color.RED);
                                        title.setX(220);
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
                            params.put("name", inputName);
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
