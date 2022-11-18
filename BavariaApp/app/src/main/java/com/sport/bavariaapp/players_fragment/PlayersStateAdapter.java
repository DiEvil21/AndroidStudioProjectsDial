package com.sport.bavariaapp.players_fragment;

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

public class PlayersStateAdapter  extends RecyclerView.Adapter<PlayersStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<PlayersState> states;

    PlayersStateAdapter(Context context, List<PlayersState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PlayersStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.players_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersStateAdapter.ViewHolder holder, int position) {
        PlayersState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());
        Glide.with(holder.imgFlag.getContext())
                .load(state.getFlag())
                .into(holder.imgFlag);
        Glide.with(holder.imgPlayer.getContext())
                .load(state.getImg())
                .into(holder.imgPlayer);


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgFlag, imgPlayer;
        final TextView name, role, country;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);
            imgPlayer = view.findViewById(R.id.imageViewPlayer);
            imgFlag = view.findViewById(R.id.imageViewFlag);

        }
    }

}
