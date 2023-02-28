package ru.sport.footballdictionary;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private final LayoutInflater inflater;

    private final List<Data> states;

    public DataAdapter(Context context, List<Data> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.dic_item, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Data state = states.get(position);
        //holder.header.setText(state.getHeader());
        holder.tv_termin.setText( Html.fromHtml ("<big><b>" +state.getWord()+ "</big></b> - " + state.getMeaning()));




    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //final ImageView img;
        final TextView tv_termin;
        ViewHolder(View view){
            super(view);
            tv_termin = view.findViewById(R.id.textV_termin);
            //header = view.findViewById(R.id.news_header);



        }
    }
}
