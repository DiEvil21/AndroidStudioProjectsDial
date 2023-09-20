package ru.sport.pockerrules;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private List<AboutListData> list;

    public AboutAdapter() {
        list = new ArrayList<>();
    }

    public void setNewsList(List<AboutListData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AboutListData aboutListData = list.get(position);

        holder.header.setText(aboutListData.getHeader());
        holder.text.setText(aboutListData.getText());

        if (aboutListData.getImg().isEmpty()) {
            holder.imageView.setVisibility(View.GONE);
        }else {
            Picasso.get().load(aboutListData.getImg()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView header;
        private TextView text;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_img);
            header = itemView.findViewById(R.id.tv_header);
            text = itemView.findViewById(R.id.tv_text);
        }
    }
}