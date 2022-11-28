package com.sport.footballcalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter   extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
    public String[] mColors = {"#cccccc","#fafafa"};
    private final LayoutInflater inflater;
    private final List<State> states;

    StateAdapter(Context context, List<State> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new StateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));

        holder.team1.setText(state.getTeam1());
        holder.team2.setText(state.getTeam2());
        holder.score.setText(state.getScore());
        holder.date.setText(state.getDate());



    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //final ImageView img1, img2;
        final TextView team1,team2, score;
        final TextView date;
        ViewHolder(View view){
            super(view);
            team2 = view.findViewById(R.id.tv_team2);
            team1 = view.findViewById(R.id.tv_team1);
            score = view.findViewById(R.id.tv_score);
            //img2 = view.findViewById(R.id.iv_team2);
            //img1 = view.findViewById(R.id.iv_team1);
            date = view.findViewById(R.id.tv_date);


        }
    }

}