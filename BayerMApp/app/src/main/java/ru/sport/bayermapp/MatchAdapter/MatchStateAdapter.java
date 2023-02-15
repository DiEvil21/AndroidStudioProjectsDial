package ru.sport.bayermapp.MatchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.sport.bayermapp.R;

public class MatchStateAdapter  extends RecyclerView.Adapter<MatchStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<MatchState> states;

    public MatchStateAdapter(Context context, List<MatchState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MatchStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.match_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchStateAdapter.ViewHolder holder, int position) {
        MatchState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());
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
        final TextView name, role, country;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);
            imgPlayer = view.findViewById(R.id.imageViewPlayer);


        }
    }

}
