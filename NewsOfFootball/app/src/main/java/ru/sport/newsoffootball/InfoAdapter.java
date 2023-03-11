package ru.sport.newsoffootball;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<Info> states;

    public InfoAdapter(Context context, List<Info> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new InfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoAdapter.ViewHolder holder, int position) {
        Info state = states.get(position);
        holder.tv_head.setText(state.getHead());
        holder.tv_date.setText(state.getDate());
        holder.tv_text.setText(state.getText());
        Glide.with(holder.img.getContext())
                .load(state.getImg())
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tv_text.getVisibility()== View.VISIBLE) {
                    holder.tv_text.setVisibility(View.GONE);
                }else {
                    holder.tv_text.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView tv_head, tv_date,tv_text;
        ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.iv_news);
            tv_date = view.findViewById(R.id.tv_date);
            tv_head = view.findViewById(R.id.tv_head);
            tv_text = view.findViewById(R.id.tv_text);


        }
    }
}