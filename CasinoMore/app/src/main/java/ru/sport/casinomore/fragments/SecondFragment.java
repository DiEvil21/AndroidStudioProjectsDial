package ru.sport.casinomore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.sport.casinomore.Item;
import ru.sport.casinomore.ItemAdapter;
import ru.sport.casinomore.R;
import ru.sport.casinomore.RetrofitClient;

public class SecondFragment extends Fragment {

    private RetrofitClient retrofitClient;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        retrofitClient = new RetrofitClient();
        LiveData<List<Item>> itemsLiveData = retrofitClient.getRoulette();
        itemsLiveData.observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                adapter.setItems(items);
            }
        });

        return view;
    }
}