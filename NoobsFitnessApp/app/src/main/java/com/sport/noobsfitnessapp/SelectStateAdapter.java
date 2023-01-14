package com.sport.noobsfitnessapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class SelectStateAdapter  extends RecyclerView.Adapter<SelectStateAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<SelectState> states;

    SelectStateAdapter(Context context, List<SelectState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public SelectStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.select_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectStateAdapter.ViewHolder holder, int position) {
        SelectState state = states.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context , ProgActivity.class);
                intent.putExtra("url",state.getUrl());
                context.startActivity(intent);
            }
        });

        holder.tv_header.setText(state.getHeader());
        holder.tv_about.setText(state.getAbout());
        Glide.with(holder.iv_background.getContext())
                .load(state.getImg())
                .into(holder.iv_background);
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_header, tv_about;
        final ImageView iv_background;


        ViewHolder(View view){
            super(view);
            tv_header = view.findViewById(R.id.tv_header);
            tv_about = view.findViewById(R.id.tv_about);
            iv_background = view.findViewById(R.id.iv_background);



        }
    }

}
