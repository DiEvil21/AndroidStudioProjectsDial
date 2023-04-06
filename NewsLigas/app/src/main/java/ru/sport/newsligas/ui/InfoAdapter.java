package ru.sport.newsligas.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.sport.newsligas.R;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    public String[] mColors = {"#BF969595","#BF757474"};
    private final List<Info> states;

    public InfoAdapter(Context context, List<Info> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.li_item, parent, false);
        return new InfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoAdapter.ViewHolder holder, int position) {
        Info state = states.get(position);
        //holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
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

            header = view.findViewById(R.id.tv_header);
            date = view.findViewById(R.id.tv_date);
            text = view.findViewById(R.id.tv_text);
            img = view.findViewById(R.id.iv_img);


        }
    }
}