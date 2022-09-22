package ru.soccer.russia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class MainStateAdapter  extends RecyclerView.Adapter<MainStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<MainState> states;

    MainStateAdapter(Context context, List<MainState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MainStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainStateAdapter.ViewHolder holder, int position) {
        MainState state = states.get(position);

        holder.name.setText(state.getName());
        holder.text.setText(state.getText());
        holder.textR.setText(state.getTextR());

        new DownloadImageTask(holder.logo).execute(state.getImg());


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView logo;
        final TextView name, text, textR;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.header45);
            logo = view.findViewById(R.id.logo);
            text = view.findViewById(R.id.textBody);
            textR = view.findViewById(R.id.textBody2);

        }
    }

}


