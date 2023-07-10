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
import ru.sport.lolkekcheburek.model.HockeyStatistic;

public class HockeyStatisticAdapter extends RecyclerView.Adapter<HockeyStatisticAdapter.ViewHolder> {
    private List<HockeyStatistic> statisticList;

    public HockeyStatisticAdapter() {
        statisticList = new ArrayList<>();
    }

    public void setStatisticList(List<HockeyStatistic> statisticList) {
        this.statisticList = statisticList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hockey_statistic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HockeyStatistic statistic = statisticList.get(position);
        holder.playerTextView.setText(statistic.getPlayer());
        holder.goalsTextView.setText(String.valueOf(statistic.getGoals()));
        holder.assistsTextView.setText(String.valueOf(statistic.getAssists()));

        // Проверяем, является ли текущий элемент первым
        if (position == 0) {
            holder.goalsTextView.setVisibility(View.GONE);
            holder.assistsTextView.setVisibility(View.GONE);
        } else {
            holder.goalsTextView.setVisibility(View.VISIBLE);
            holder.assistsTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerTextView;
        private TextView goalsTextView;
        private TextView assistsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerTextView = itemView.findViewById(R.id.playerTextView);
            goalsTextView = itemView.findViewById(R.id.goalsTextView);
            assistsTextView = itemView.findViewById(R.id.assistsTextView);
        }
    }
}

