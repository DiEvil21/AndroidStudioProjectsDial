package com.sport.footballspravka.rules_classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sport.footballspravka.R;

import java.util.List;

public class RulesStateAdapter  extends RecyclerView.Adapter<RulesStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    public String[] mColors = {"#fafafa","#ffffff"};
    private final List<RulesState> states;

    RulesStateAdapter(Context context, List<RulesState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RulesStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_rules, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RulesStateAdapter.ViewHolder holder, int position) {
        RulesState state = states.get(position);
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        holder.textQuestion.setText(state.getHeader());
        holder.textAnswer.setText(state.getText());
        Glide.with(holder.imageView.getContext())
                .load(state.getImg())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView textQuestion, textAnswer;
        ViewHolder(View view){
            super(view);
            textQuestion = view.findViewById(R.id.textViewQuestion);
            textAnswer = view.findViewById(R.id.textViewAnswer);
            imageView = view.findViewById(R.id.imageViewInfo);


        }
    }
}
