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
import ru.sport.lolkekcheburek.model.HockeyMatch;
import ru.sport.lolkekcheburek.viewmodel.HockeyMatchViewModel;

public class HockeyMatchFragment extends Fragment {
    private RecyclerView recyclerView;
    private HockeyMatchAdapter adapter;
    private HockeyMatchViewModel viewModel;

    public HockeyMatchFragment() {
        // Обязательный пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hockey_match, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HockeyMatchAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HockeyMatchViewModel.class);
        viewModel.getMatches().observe(getViewLifecycleOwner(), new Observer<List<HockeyMatch>>() {
            @Override
            public void onChanged(List<HockeyMatch> matchList) {
                adapter.setMatchList(matchList);
            }
        });

        return view;
    }
}
