package com.example.fitnesscomrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class workouts_listAdapter extends RecyclerView.Adapter<workouts_listAdapter.workouts_listViewHolder> {

    private List<String> names;

    public workouts_listAdapter(List<String> names){
        this.names = names;
    }

    public static class workouts_listViewHolder extends  RecyclerView.ViewHolder{
        public TextView textView;

        public workouts_listViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.workouts_list_textView);
        }
    }

    @NonNull
    @Override
    public workouts_listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.workouts_list_textview,parent,false);
        workouts_listViewHolder workoutsListViewHolder= new workouts_listViewHolder(v);
        return workoutsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull workouts_listViewHolder holder, int position) {
        holder.textView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
