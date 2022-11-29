package com.sport.footballspravka.termin_classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sport.footballspravka.R;

import java.util.List;


public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
    boolean click;
    public String[] mColors = {"#fafafa","#ffffff"};
    private final LayoutInflater inflater;
    private final List<State> states;

    StateAdapter(Context context, List<State> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        click = true;
        holder.textView.setText(state.getTermin());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click) {
                    holder.textView.setText(state.getAbout());
                    click = !click;
                }else {
                    holder.textView.setText(state.getTermin());
                    click = !click;
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;

        ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.textView);


        }
    }

}
