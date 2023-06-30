package ru.sport.esheodnoprilojenie;

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModel> newsList;

    public NewsAdapter() {
        newsList = new ArrayList<>();
    }

    public void setNewsList(List<NewsModel> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel news = newsList.get(position);
        holder.titleTextView.setText(news.getTitle());
        holder.shortTextView.setText(news.getShortText());
        holder.fullTextView.setText(news.getFullText());
        Picasso.get().load(news.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView shortTextView;
        TextView fullTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            shortTextView = itemView.findViewById(R.id.shortTextView);
            fullTextView = itemView.findViewById(R.id.fullTextView);

            // Обработка нажатия на краткий текст для разворачивания полного текста
            shortTextView.setOnClickListener(v -> {
                if (fullTextView.getVisibility() == View.VISIBLE) {
                    fullTextView.setVisibility(View.GONE);
                } else {
                    fullTextView.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}

