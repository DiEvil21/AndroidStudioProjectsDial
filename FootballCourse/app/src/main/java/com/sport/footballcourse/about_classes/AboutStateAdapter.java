package com.sport.footballcourse.about_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sport.footballcourse.R;

import java.util.List;

public class AboutStateAdapter extends RecyclerView.Adapter<AboutStateAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<AboutState> states;

    AboutStateAdapter(Context context, List<AboutState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public AboutStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.about_item, parent, false);
        return new AboutStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutStateAdapter.ViewHolder holder, int position) {
        AboutState state = states.get(position);
        holder.header.setText(state.getHeader());
        holder.text.setText(state.getText());

        Glide.with(holder.img.getContext())
                .load(state.getImage())
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView header, text;
        ViewHolder(View view){
            super(view);

            header = view.findViewById(R.id.news_header);

            text = view.findViewById(R.id.news_text);
            img = view.findViewById(R.id.img);


        }
    }
}
