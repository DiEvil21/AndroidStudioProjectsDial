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

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<FactsData> states;

    public FactsAdapter(Context context, List<FactsData> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public FactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.facts_list_item, parent, false);
        return new FactsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FactsAdapter.ViewHolder holder, int position) {
        FactsData state = states.get(position);
        //holder.tv_header.setText(state.getHeader());
        holder.tv_date.setText(state.getYear());
        holder.tv_event.setText(state.getEvent());




    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_event, tv_date;
        ViewHolder(View view){
            super(view);

            tv_date = view.findViewById(R.id.tv_event_date);
            tv_event = view.findViewById(R.id.tv_event);




        }
    }
}