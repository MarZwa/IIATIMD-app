package com.example.fitnesscomrade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.database.Workout;

import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder> {

    private Context context;
    private List<Workout> workoutList;

    public WorkoutListAdapter(Context context) {
        this.context = context;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutListAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(this.workoutList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return this.workoutList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
        }
    }
}
