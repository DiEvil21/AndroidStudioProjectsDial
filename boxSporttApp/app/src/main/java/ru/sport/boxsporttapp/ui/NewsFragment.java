package ru.sport.boxsporttapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sport.boxsporttapp.R;


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



public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private NewsViewModel viewModel;

    public NewsFragment() {
        // Обязательный пустой конструктор
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getNews().observe(getViewLifecycleOwner(), new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsList) {
                adapter.setNewsList(newsList);
            }
        });

        return view;
    }
}