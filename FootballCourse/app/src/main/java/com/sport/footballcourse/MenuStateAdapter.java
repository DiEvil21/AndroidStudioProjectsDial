package com.sport.footballcourse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sport.footballcourse.about_classes.AboutActivity;

import java.util.List;

public class MenuStateAdapter  extends RecyclerView.Adapter<MenuStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<MenuState> states;

    MenuStateAdapter(Context context, List<MenuState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MenuStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.menu_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuStateAdapter.ViewHolder holder, int position) {
        MenuState state = states.get(position);

        //holder.name.setText(state.getName());
        Glide.with(holder.img.getContext())
                .load(state.getImg())
                .into(holder.img);
        holder.tv_header.setText(state.getHeader());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context , AboutActivity.class);
                intent.putExtra("url",state.getUrl());
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_header;
        //final TextView name, role, country;
        final ImageView img;
        ViewHolder(View view){
            super(view);
            //name = view.findViewById(R.id.textViewName);
            tv_header = view.findViewById(R.id.tv_header);
            img = view.findViewById(R.id.img);

        }
    }

}
