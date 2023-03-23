package ru.sport.aplligaapp.states;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sport.aplligaapp.R;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Stats> states;

    public StatsAdapter(Context context, List<Stats> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.stats_item, parent, false);
        return new StatsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatsAdapter.ViewHolder holder, int position) {
        Stats state = states.get(position);
        holder.st0.setText(state.getS0());
        holder.st1.setText(state.getS1());
        holder.st2.setText(state.getS2());
        holder.st3.setText(state.getS3());
        holder.st4.setText(state.getS4());
        holder.st5.setText(state.getS5());
        holder.st6.setText(state.getS6());





    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView st0, st1,st2,st3,st4,st5,st6;

        ViewHolder(View view){
            super(view);
            st0 = view.findViewById(R.id.stcountt);
            st1 = view.findViewById(R.id.st1);
            st2 = view.findViewById(R.id.st2);
            st3 = view.findViewById(R.id.st3);
            st4 = view.findViewById(R.id.st4);
            st5 = view.findViewById(R.id.st5);
            st6 = view.findViewById(R.id.st6);



        }
    }

}
