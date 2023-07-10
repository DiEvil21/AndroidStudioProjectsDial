package ru.sport.lolkekcheburek.ui;

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

import ru.sport.lolkekcheburek.R;
import ru.sport.lolkekcheburek.model.HockeyStatistic;
import ru.sport.lolkekcheburek.viewmodel.HockeyStatisticViewModel;

public class HockeyStatisticFragment extends Fragment {
    private RecyclerView recyclerView;
    private HockeyStatisticAdapter adapter;
    private HockeyStatisticViewModel viewModel;

    public HockeyStatisticFragment() {
        // Обязательный пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hockey_statistic, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HockeyStatisticAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HockeyStatisticViewModel.class);
        viewModel.getStatistics().observe(getViewLifecycleOwner(), new Observer<List<HockeyStatistic>>() {
            @Override
            public void onChanged(List<HockeyStatistic> statisticList) {
                adapter.setStatisticList(statisticList);
            }
        });

        return view;
    }
}
