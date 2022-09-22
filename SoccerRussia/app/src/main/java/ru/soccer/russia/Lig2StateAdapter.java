package ru.soccer.russia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Lig2StateAdapter extends RecyclerView.Adapter<Lig2StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<LigState2> states;

    Lig2StateAdapter(Context context, List<LigState2> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public Lig2StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lig_list2_item, parent, false);
        return new Lig2StateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Lig2StateAdapter.ViewHolder holder, int position) {
        LigState2 state = states.get(position);
        holder.st1.setText(state.getSt1());
        holder.st2.setText(state.getSt2());
        holder.st3.setText(state.getSt3());
        holder.st4.setText(state.getSt4());
        holder.st5.setText(state.getSt5());
        holder.st6.setText(state.getSt6());
        holder.st7.setText(state.getSt7());
        holder.st8.setText(state.getSt8());
        holder.count.setText(state.getCount());
        new DownloadImageTask(holder.sti).execute(state.getSti());



    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sti;
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
            sti = view.findViewById(R.id.sti);


        }
    }

}
