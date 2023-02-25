package ru.sport.chelsiapp.fragments.matchs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.sport.chelsiapp.R;

public class MatchsStateAdapter  extends RecyclerView.Adapter<MatchsStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<MatchState> states;

    MatchsStateAdapter(Context context, List<MatchState> states) {
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
        MatchState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());
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
        final TextView name, country, role;

        ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.imageViewPlayer);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);



        }
    }

}
