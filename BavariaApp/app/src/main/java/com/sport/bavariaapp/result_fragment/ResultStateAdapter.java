package com.sport.bavariaapp.result_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sport.bavariaapp.R;

import java.util.List;

public class ResultStateAdapter   extends RecyclerView.Adapter<ResultStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<ResultState> states;

    ResultStateAdapter(Context context, List<ResultState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ResultStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.result_list_item, parent, false);
        return new ResultStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultStateAdapter.ViewHolder holder, int position) {
        ResultState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());
        holder.score.setText(state.getScore());
        Glide.with(holder.imgPlayer.getContext())
                .load(state.getImg())
                .into(holder.imgPlayer);


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPlayer;
        final TextView name, role, country, score;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);
            imgPlayer = view.findViewById(R.id.imageViewPlayer);
            score = view.findViewById(R.id.textViewScore);


        }
    }

}
