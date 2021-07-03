package com.example.fitnesscomrade.myWorkoutsRecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesscomrade.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view;
    private CardView cardview;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);
        cardview = itemView.findViewById(R.id.cardview);
    }

    public TextView getView(){
        return view;
    }

    public CardView getCardView() {
        return cardview;
    }
}
