package com.sport.ligaapp.result_fragment_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sport.ligaapp.DownloadImageTask;
import com.sport.ligaapp.R;

import java.util.List;

public class ResultStateAdapter   extends RecyclerView.Adapter<ResultStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<ResultState> states;

    ResultStateAdapter(Context context, List<ResultState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ResultStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.result_list_item, parent, false);
        return new ResultStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultStateAdapter.ViewHolder holder, int position) {
        ResultState state = states.get(position);
        if (state.getScore().equals("")) {
            holder.name.setTextSize(30);
        }else {
            holder.name.setTextSize(15);
        }
        holder.name.setText(state.getName());
        holder.country.setText(state.getDate());
        holder.score.setText(state.getScore());
        new DownloadImageTask(holder.img1).execute(state.getImg1());
        new DownloadImageTask(holder.img2).execute(state.getImg1());


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img1, img2;
        final TextView name, country, score;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            img2 = view.findViewById(R.id.imageViewImg2);
            country = view.findViewById(R.id.textViewCountry);
            img1 = view.findViewById(R.id.imageViewImg1);
            score = view.findViewById(R.id.textViewScore);


        }
    }

}
