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
import ru.sport.lolkekcheburek.model.HockeyNews;
import ru.sport.lolkekcheburek.viewmodel.HockeyNewsViewModel;

public class HockeyNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private HockeyNewsAdapter adapter;
    private HockeyNewsViewModel viewModel;

    public HockeyNewsFragment() {
        // Обязательный пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hockey_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HockeyNewsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HockeyNewsViewModel.class);
        viewModel.getNews().observe(getViewLifecycleOwner(), new Observer<List<HockeyNews>>() {
            @Override
            public void onChanged(List<HockeyNews> newsList) {
                adapter.setNewsList(newsList);
            }
        });

        return view;
    }
}
