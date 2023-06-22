package ru.sport.sportnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsItem> newsItems;

    public NewsAdapter(Context context, List<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        }

        NewsItem newsItem = (NewsItem) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.newsImageView);
        TextView titleTextView = convertView.findViewById(R.id.newsTitleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.newsDescriptionTextView);

        Picasso.get().load(newsItem.getImageUrl()).into(imageView);
        titleTextView.setText(newsItem.getTitle());
        descriptionTextView.setText(newsItem.getDescription());

        return convertView;
    }
}

