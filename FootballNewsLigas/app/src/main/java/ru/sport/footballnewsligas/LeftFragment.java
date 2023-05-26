package ru.sport.footballnewsligas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.sport.footballnewsligas.fragments_logic.NewsAdapter;
import ru.sport.footballnewsligas.fragments_logic.NewsData;


public class LeftFragment extends Fragment {
    View view;
    NewsViewModel newsViewModel;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_left, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.list);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNewsLeft().observe(getViewLifecycleOwner(), new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsData) {
                NewsAdapter adapter = new NewsAdapter(view.getContext(), newsData);
                // устанавливаем для списка адаптер
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

}