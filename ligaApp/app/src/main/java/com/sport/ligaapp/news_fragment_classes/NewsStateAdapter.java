package com.sport.ligaapp.news_fragment_classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sport.ligaapp.DownloadImageTask;
import com.sport.ligaapp.R;

import java.util.List;

public class NewsStateAdapter extends RecyclerView.Adapter<NewsStateAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    public String[] mColors = {"#fafafa","#ffffff"};
    private final List<NewsState> states;

    NewsStateAdapter(Context context, List<NewsState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public NewsStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsStateAdapter.ViewHolder holder, int position) {
        NewsState state = states.get(position);

        holder.header.setText(state.getHeader());
        holder.date.setText(state.getDate());
        holder.text.setText(state.getText());

        new DownloadImageTask(holder.img).execute(state.getImage());


    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView header, date, text;
        ViewHolder(View view){
            super(view);

            header = view.findViewById(R.id.news_header);
            date = view.findViewById(R.id.news_date);
            text = view.findViewById(R.id.news_text);
            img = view.findViewById(R.id.img);


        }
    }
}
