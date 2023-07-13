package ru.sport.newyorkrangers.statistics;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.sport.newyorkrangers.R;


public class StatisticsFragment extends Fragment {

    private RecyclerView recyclerView;
    private StatisticsAdapter adapter;
    private StatisticsViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StatisticsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        viewModel.getStatistics().observe(getViewLifecycleOwner(), new Observer<List<StatisticsData>>() {
            @Override
            public void onChanged(List<StatisticsData> statisticList) {
                adapter.setStatisticList(statisticList);
            }
        });

        return view;
    }
}