package com.sport.pushupsapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
    boolean click;
    public String[] mColors = {"#fafafa","#ffffff"};
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

        holder.tv_xp.setText(state.getXp() + " XP");
        holder.tv_name.setText(state.getName());
        Glide.with(holder.iv_icon.getContext())
                .load(state.getImg())
                .into(holder.iv_icon);
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_name, tv_xp;
        final ImageView iv_icon;

        ViewHolder(View view){
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_xp = view.findViewById(R.id.tv_xp);
            iv_icon = view.findViewById(R.id.iv_icon);


        }
    }

}
