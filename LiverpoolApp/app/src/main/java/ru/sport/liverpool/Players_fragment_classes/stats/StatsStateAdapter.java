package ru.sport.liverpool.Players_fragment_classes.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sport.liverpool.DownloadImageTask;
import ru.sport.liverpool.R;


public class StatsStateAdapter extends RecyclerView.Adapter<StatsStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<StatsState> states;

    public StatsStateAdapter(Context context, List<StatsState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StatsStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.stats_list_item, parent, false);
        return new StatsStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatsStateAdapter.ViewHolder holder, int position) {
        StatsState state = states.get(position);
        holder.st1.setText(state.getSt1());
        holder.st2.setText(state.getSt2());
        holder.st3.setText(state.getSt3());
        holder.st4.setText(state.getSt4());
        holder.st5.setText(state.getSt5());
        holder.st6.setText(state.getSt6());
        holder.st7.setText(state.getSt7());
        holder.st8.setText(state.getSt8());
        holder.count.setText(state.getCount());




    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView st1,st2,st3,st4,st5,st6,st7,st8, count;

        ViewHolder(View view){
            super(view);
            count = view.findViewById(R.id.stcount);
            st1 = view.findViewById(R.id.st1);
            st2 = view.findViewById(R.id.st2);
            st3 = view.findViewById(R.id.st3);
            st4 = view.findViewById(R.id.st4);
            st5 = view.findViewById(R.id.st5);
            st6 = view.findViewById(R.id.st6);
            st7 = view.findViewById(R.id.st7);
            st8 = view.findViewById(R.id.st8);



        }
    }

}
