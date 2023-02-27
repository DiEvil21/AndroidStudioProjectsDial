package ru.sport.spravochnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class InfoForecastAdapter extends RecyclerView.Adapter<InfoForecastAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<InfoForecast> states;

    InfoForecastAdapter(Context context, List<InfoForecast> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public InfoForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.insfo_forecast_item, parent, false);
        return new InfoForecastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoForecastAdapter.ViewHolder holder, int position) {
        InfoForecast state = states.get(position);
        holder.textV_header.setText(state.getHeader());
        holder.textV_about.setText(state.getAbout());



    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        final TextView textV_header, textV_about;
        ViewHolder(View view){
            super(view);

            textV_header = view.findViewById(R.id.textV_header);
            textV_about = view.findViewById(R.id.textV_about);



        }
    }
}
