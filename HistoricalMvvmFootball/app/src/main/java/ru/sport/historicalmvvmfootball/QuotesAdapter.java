package ru.sport.historicalmvvmfootball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<QuotesData> states;

    public QuotesAdapter(Context context, List<QuotesData> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public QuotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.quotes_list_item, parent, false);
        return new QuotesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuotesAdapter.ViewHolder holder, int position) {
        QuotesData state = states.get(position);
        //holder.tv_header.setText(state.getHeader());
        holder.tv_athor.setText(state.getAuthor());
        holder.tv_date.setText(state.getYear());
        holder.tv_quote.setText(state.getText());
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
        final TextView tv_athor, tv_quote, tv_date;
        ViewHolder(View view){
            super(view);

            tv_date = view.findViewById(R.id.tv_date);
            img = view.findViewById(R.id.imageView);
            tv_athor = view.findViewById(R.id.tv_athor);
            tv_quote = view.findViewById(R.id.tv_quote);




        }
    }
}