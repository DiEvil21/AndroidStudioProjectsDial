package ru.sport.liverpool.Players_fragment_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sport.liverpool.DownloadImageTask;
import ru.sport.liverpool.R;

public class PlayersStateAdapter  extends RecyclerView.Adapter<PlayersStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<PlayersState> states;
    View view;
    boolean sw = false;

    PlayersStateAdapter(Context context, List<PlayersState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PlayersStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.players_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersStateAdapter.ViewHolder holder, int position) {
        PlayersState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());
        new DownloadImageTask(holder.imgFlag).execute(state.getFlag());
        new DownloadImageTask(holder.imgPlayer).execute(state.getImg());
        holder.info.setText("");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw) {
                    holder.info.setText(state.getInfo());
                }else {
                    holder.info.setText("");
                }

                System.out.println("privet dalbaeb");
                sw = !sw;
            }
        });


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgFlag, imgPlayer;
        final TextView name, role, country, info;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);
            imgPlayer = view.findViewById(R.id.imageViewPlayer);
            imgFlag = view.findViewById(R.id.imageViewFlag);
            info = view.findViewById(R.id.textView16);

        }
    }

}
