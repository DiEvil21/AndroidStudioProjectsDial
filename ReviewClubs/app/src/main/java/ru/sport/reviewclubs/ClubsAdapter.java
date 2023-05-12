package ru.sport.reviewclubs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<Clubs> states;

    public ClubsAdapter(Context context, List<Clubs> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ClubsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.clubs_item, parent, false);
        return new ClubsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClubsAdapter.ViewHolder holder, int position) {
        Clubs state = states.get(position);
        holder.tv_team.setText(state.getTeam());
        holder.tv_owner.setText("Owner: " +state.getOwner());
        holder.tv_manager.setText(holder.tv_manager.getContext().getString(R.string.manager)+state.getManager());
        holder.tv_stadium.setText("Stadium: "+state.getStadium());
        holder.tv_country.setText("Location: "+state.getCountry());
        holder.tv_about.setText(state.getAbout());
        Glide.with(holder.logo.getContext())
                .load(state.getLogo())
                .into(holder.logo);


    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView logo;
        final TextView tv_team,tv_country, tv_stadium,tv_manager,tv_about,tv_owner;
        ViewHolder(View view){
            super(view);
            //tv_termin = view.findViewById(R.id.textV_termin);
            //header = view.findViewById(R.id.news_header);
            tv_team = view.findViewById(R.id.tv_team);
            tv_country = view.findViewById(R.id.tv_country);
            tv_stadium = view.findViewById(R.id.tv_stadium);
            tv_manager = view.findViewById(R.id.tv_manager);
            tv_about = view.findViewById(R.id.tv_about);
            tv_owner = view.findViewById(R.id.tv_owner);
            logo = view.findViewById(R.id.iv_logo);



        }
    }
}
