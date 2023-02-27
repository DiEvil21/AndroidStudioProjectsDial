package ru.sport.aplligaapp.fragments.wall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.sport.aplligaapp.R;

public class WallStateAdapter extends RecyclerView.Adapter<WallStateAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<WallState> states;

    WallStateAdapter(Context context, List<WallState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public WallStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.wall_item, parent, false);
        return new WallStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallStateAdapter.ViewHolder holder, int position) {
        WallState state = states.get(position);
        holder.header.setText(state.getHeader());
        holder.date.setText(state.getDate());
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