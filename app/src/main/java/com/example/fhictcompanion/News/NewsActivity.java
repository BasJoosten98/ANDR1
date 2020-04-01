package com.example.fhictcompanion.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.fhictcompanion.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsActivity extends AppCompatActivity implements ITaskReceiver {


    private ListView listViewNews;
    private String fontysToken;

    private int REQUESTCODE_GET_NEWS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent sender = getIntent();
        fontysToken = sender.getStringExtra("token");
        listViewNews = findViewById(R.id.lvFontysNews);

        loadNews();
    }

    private void loadNews(){
        new JSONTaskNews(this, REQUESTCODE_GET_NEWS).execute(fontysToken);
    }

    private void setListView(List<NewsPost> posts){
        listViewNews.setAdapter(new CustomNewsListAdapter(getApplicationContext(), posts));
    }

    @Override
    public void OnTaskReceived(Object data, int requestCode) {
        if(REQUESTCODE_GET_NEWS == requestCode){
            if(data != null){
                if(data instanceof ArrayList){
                    ArrayList<NewsPost> newsPosts = (ArrayList<NewsPost>)data;
                    setListView(newsPosts);
                }
            }
        }
    }
}