package ru.sport.lolkekcheburek.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sport.lolkekcheburek.R;
import ru.sport.lolkekcheburek.model.HockeyMatch;

public class HockeyMatchAdapter extends RecyclerView.Adapter<HockeyMatchAdapter.ViewHolder> {
    private List<HockeyMatch> matchList;

    public HockeyMatchAdapter() {
        matchList = new ArrayList<>();
    }

    public void setMatchList(List<HockeyMatch> matchList) {
        this.matchList = matchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hockey_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HockeyMatch match = matchList.get(position);
        holder.team1TextView.setText(match.getTeam1());
        holder.team2TextView.setText(match.getTeam2());
        holder.scoreTextView.setText(match.getScore());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView team1TextView;
        private TextView team2TextView;
        private TextView scoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team1TextView = itemView.findViewById(R.id.team1TextView);
            team2TextView = itemView.findViewById(R.id.team2TextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}

