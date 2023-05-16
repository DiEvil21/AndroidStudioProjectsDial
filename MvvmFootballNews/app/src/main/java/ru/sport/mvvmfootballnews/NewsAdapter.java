package ru.sport.mvvmfootballnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<NewsData> states;

    public NewsAdapter(Context context, List<NewsData> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.news_list_item, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        NewsData state = states.get(position);
        holder.tv_header.setText(state.getHeader());
        holder.tv_text.setText(state.getText());
        holder.tv_date.setText(state.getDate());
        Glide.with(holder.img.getContext())
                .load(state.getImg())
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView tv_header, tv_text, tv_date;
        ViewHolder(View view){
            super(view);

            tv_header = view.findViewById(R.id.tv_header);
            tv_text = view.findViewById(R.id.tv_text);
            tv_date = view.findViewById(R.id.tv_date);
            img = view.findViewById(R.id.imageView);




        }
    }
}
