package com.sport.ligaapp.table_fragment_classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sport.ligaapp.DownloadImageTask;
import com.sport.ligaapp.R;

import java.util.List;
public class TableStateAdapter extends RecyclerView.Adapter<TableStateAdapter.ViewHolder>{
    public int[] mSizes = {20, 15, 15,15,15};
    public String[] mColors = {"#03a609","#ffffff","#ffffff","#ffffff","#ffffff"};
    private final LayoutInflater inflater;
    private final List<TableState> states;

    public TableStateAdapter(Context context, List<TableState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public TableStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.table_item, parent, false);
        return new TableStateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableStateAdapter.ViewHolder holder, int position) {
        TableState state = states.get(position);
        holder.st1.setTextColor(Color.parseColor(mColors[position % 5]));
        holder.st1.setTextSize(mSizes[position % 5]);
        holder.st1.setText(state.getSt1());
        holder.st2.setText(state.getSt2());
        holder.st3.setText(state.getSt3());
        holder.st4.setText(state.getSt4());
        holder.st5.setText(state.getSt5());
        holder.st6.setText(state.getSt6());
        holder.st7.setText(state.getSt7());
        holder.st8.setText(state.getSt8());
        //holder.count.setText(state.getCount());
        new DownloadImageTask(holder.img).execute(state.getSt0());



    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView st1,st2,st3,st4,st5,st6,st7,st8, count;
        final ImageView img;

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
            img = view.findViewById(R.id.imageView);



        }
    }

}
