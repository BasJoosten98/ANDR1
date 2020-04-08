package com.example.fhictcompanion.News;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fhictcompanion.R;

import org.w3c.dom.Text;

import java.util.List;

public class CustomNewsListAdapter extends BaseAdapter {
    private List<NewsPost> news;
    private LayoutInflater layoutInflater;

    public CustomNewsListAdapter(Context context, List<NewsPost> news){
        this.news = news;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_news_post, parent, false);
        }

        TextView tvID = convertView.findViewById(R.id.tvNewsPostID);
        TextView tvTitle = convertView.findViewById(R.id.tvNewsPostTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvNewsPostAuthor);
        TextView tvContent = convertView.findViewById(R.id.tvNewsPostContent);
        ImageView ivImage = convertView.findViewById(R.id.ivNewsPostImage);

        tvID.setText("Post #" + (position + 1));
        tvTitle.setText(news.get(position).getTitle());
        tvAuthor.setText(news.get(position).getAuthor());
        tvContent.setText(Html.fromHtml(news.get(position).getContent()));
        ivImage.setImageBitmap(news.get(position).getImage()); //if image is null, it will simply display nothing

        return convertView;
    }
}
