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
    private OnListItemListener mOnListItemListener;

    public workouts_listAdapter(List<String> names, OnListItemListener onListItemListener){
        this.names = names;
        this.mOnListItemListener = onListItemListener;
    }

    public static class workouts_listViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView textView;
        OnListItemListener onListItemListener;

        public workouts_listViewHolder(View v, OnListItemListener onListItemListener){
            super(v);
            textView = v.findViewById(R.id.workoutTitle);
            this.onListItemListener = onListItemListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemListener.onListClick(getAdapterPosition());
        }
    }

    public interface OnListItemListener{
        void onListClick(int position);
    }

    @NonNull
    @Override
    public workouts_listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.workouts_list_textview,parent,false);
        workouts_listViewHolder workoutsListViewHolder= new workouts_listViewHolder(v, mOnListItemListener);
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
