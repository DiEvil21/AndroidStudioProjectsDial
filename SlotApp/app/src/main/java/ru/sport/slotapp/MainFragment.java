package ru.sport.slotapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    View view;
    TextView tv_title, tv_text_before, tv_text_after;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        tv_title = view.findViewById(R.id.tv_title);
        tv_text_before = view.findViewById(R.id.tv_text_before);
        tv_text_after = view.findViewById(R.id.tv_text_after);
        img = view.findViewById(R.id.imageView);
        fetchData();

        return view;
    }

    private void fetchData() {
        ApiService apiService = MyApplication.getApiService();
        Call<Data> call = apiService.getMain();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Data data = response.body();



                    tv_title.setText(data.getTitle());
                    tv_text_before.setText(data.getText_before());
                    tv_text_after.setText(data.getText_after());
                    Glide.with(requireContext()) // Используем контекст фрагмента
                            .load(data.getImg())
                            .into(img);
                } else {
                    // errors
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                // errors
            }
        });
    }
}