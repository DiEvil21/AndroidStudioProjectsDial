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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class PlayersStateAdapter  extends RecyclerView.Adapter<PlayersStateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<PlayersState> states;

    PlayersStateAdapter(Context context, List<PlayersState> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PlayersStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.players_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersStateAdapter.ViewHolder holder, int position) {
        PlayersState state = states.get(position);

        holder.name.setText(state.getName());
        holder.country.setText(state.getCountry());
        holder.role.setText(state.getRole());

        new DownloadImageTask(holder.imgFlag).execute(state.getFlag());
        new DownloadImageTask(holder.imgPlayer).execute(state.getImg());


    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgFlag, imgPlayer;
        final TextView name, role, country;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textViewName);
            role = view.findViewById(R.id.textViewRole);
            country = view.findViewById(R.id.textViewCountry);
            imgPlayer = view.findViewById(R.id.imageViewPlayer);
            imgFlag = view.findViewById(R.id.imageViewFlag);

        }
    }

}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
