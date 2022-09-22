package ru.soccer.russia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Lig1StateAdapter extends RecyclerView.Adapter<Lig1StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<LigState1> states;

    Lig1StateAdapter(Context context, List<LigState1> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public Lig1StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lig_list1_item, parent, false);
        return new Lig1StateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Lig1StateAdapter.ViewHolder holder, int position) {
        LigState1 state = states.get(position);
        holder.textViewRight.setText(state.getRight());
        holder.textViewLeft.setText(state.getLeft());
        new DownloadImageTask(holder.imageViewLeft).execute(state.getImgLeft());
        new DownloadImageTask(holder.imageViewRight).execute(state.getImgRight());


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewLeft, textViewRight;
        final ImageView imageViewLeft, imageViewRight;

        ViewHolder(View view){
            super(view);
            textViewLeft = view.findViewById(R.id.textViewLeft);
            textViewRight = view.findViewById(R.id.textViewRight);
            imageViewLeft = view.findViewById(R.id.imageViewLeft);
            imageViewRight = view.findViewById(R.id.imageViewRight);


        }
    }

}