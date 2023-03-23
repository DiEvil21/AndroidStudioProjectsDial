package ru.sport.aplligaapp.states;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sport.aplligaapp.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Calendar> states;

    public CalendarAdapter(Context context, List<Calendar> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.calendar_item, parent, false);
        return new CalendarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.ViewHolder holder, int position) {
        Calendar state = states.get(position);
        holder.tv_date.setText(state.getDate());
        holder.tv_owner.setText(state.getOwner());
        holder.tv_score.setText(state.getScore());
        holder.tv_quest.setText(state.getQuest());




    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_date, tv_owner, tv_score, tv_quest;

        ViewHolder(View view){
            super(view);
            tv_date = view.findViewById(R.id.tv_date);
            tv_owner = view.findViewById(R.id.tv_owner);
            tv_score = view.findViewById(R.id.tv_score);
            tv_quest = view.findViewById(R.id.tv_quest);




        }
    }

}