package com.example.fitnesscomrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class workouts_detailAdapter extends RecyclerView.Adapter<workouts_detailAdapter.workouts_detailViewHolder>{

    private List<String> exercises;

    public workouts_detailAdapter(List<String> exercises){
        this.exercises = exercises;
    }

    public static class workouts_detailViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;

        public workouts_detailViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.workoutTitle);
        }
    }

    @NonNull
    @Override
    public workouts_detailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_detail_exercise,parent,false);
        workouts_detailAdapter.workouts_detailViewHolder workoutsDetailViewHolder= new workouts_detailAdapter.workouts_detailViewHolder(v);
        return workoutsDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull workouts_detailAdapter.workouts_detailViewHolder holder, int position) {
        holder.textView.setText(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

}
