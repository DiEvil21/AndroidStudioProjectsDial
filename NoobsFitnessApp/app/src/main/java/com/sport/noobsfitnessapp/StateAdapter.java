package com.sport.noobsfitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<State> states;

    StateAdapter(Context context, List<State> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        State state = states.get(position);


        holder.tv_name.setText(state.getName());
        holder.tv_kol.setText(state.getKol());
        holder.tv_rep.setText(state.getRep());
        Glide.with(holder.iv_img.getContext())
                .load(state.getImg())
                .into(holder.iv_img);
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_name, tv_rep, tv_kol;
        final ImageView iv_img;

        ViewHolder(View view){
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_kol = view.findViewById(R.id.tv_kol);
            tv_rep = view.findViewById(R.id.tv_rep);
            iv_img = view.findViewById(R.id.iv_img);



        }
    }

}