package ru.sport.newyorkrangers.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ru.sport.newyorkrangers.R;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private List<StatisticsData> statisticList;

    public StatisticsAdapter() {
        statisticList = new ArrayList<>();
    }

    public void setStatisticList(List<StatisticsData> statisticList) {
        this.statisticList = statisticList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatisticsData statistic = statisticList.get(position);
        holder.playerTextView.setText(statistic.getPlayer());
        holder.goalsTextView.setText(String.valueOf(statistic.getGoals()));
        holder.assistsTextView.setText(String.valueOf(statistic.getAssists()));


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
