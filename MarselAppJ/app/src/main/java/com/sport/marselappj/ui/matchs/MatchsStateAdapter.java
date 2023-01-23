package com.sport.marselappj.ui.matchs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sport.marselappj.R;

import java.util.List;

public class MatchsStateAdapter  extends RecyclerView.Adapter<MatchsStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<MatchsState> states;

    MatchsStateAdapter(Context context, List<MatchsState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MatchsStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.matchs_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchsStateAdapter.ViewHolder holder, int position) {
        MatchsState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.date.setText(state.getDate());
        holder.score.setText(state.getScore());
        Glide.with(holder.img.getContext())
                .load(state.getImg())
                .into(holder.img);

    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView name, date, country,score;

        ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.imageView);
            name = view.findViewById(R.id.textViewName);
            date = view.findViewById(R.id.textViewDate);
            country = view.findViewById(R.id.textViewCountry);
            score = view.findViewById(R.id.textViewScore);


        }
    }

}

