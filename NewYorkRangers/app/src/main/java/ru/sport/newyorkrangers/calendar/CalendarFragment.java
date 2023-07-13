package ru.sport.newyorkrangers.calendar;

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

import java.util.Calendar;
import java.util.List;

import ru.sport.newyorkrangers.R;


public class CalendarFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarAdapter adapter;
    private CalendarViewModel viewModel;



    public CalendarFragment() {
        // Required empty public constructor
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CalendarAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        viewModel.getMatches().observe(getViewLifecycleOwner(), new Observer<List<CalendarData>>() {
            @Override
            public void onChanged(List<CalendarData> matchList) {
                adapter.setMatchList(matchList);
            }
        });

        return view;
    }
}