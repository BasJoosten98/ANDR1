package com.example.fhictcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsActivity extends AppCompatActivity {

    private String urlNews = "https://api.fhict.nl/newsfeeds/Fhict/posts";
    private ListView listViewNews;
    private String fontysToken;

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
        new JSONTask().execute(urlNews, fontysToken);
    }

    public void SetListView(List<NewsPost> posts){
        listViewNews.setAdapter(new CustomNewsListAdapter(getApplicationContext(), posts));
    }


    private class JSONTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if(strings.length <= 1){return  null;} //no URL, token given
            String urlString = strings[0];
            String token = strings[1];

            String result = null;

            try {
                //Create URL
                URL url = new URL(urlString);
                //Create HttpURLConnection, by opening connection
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //Set HttpURLConnection properties
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                //Make connection
                connection.connect();
                //Get InputStream from URLConnection
                InputStream is = connection.getInputStream();
                Scanner scanner = new Scanner(is);
                result = scanner.useDelimiter("\\Z").next();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                List<NewsPost> temp = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject news = jsonArray.getJSONObject(i);
                        String title = news.getString("title");
                        String author = news.getString("author");
                        String content = news.getString("content");

                        NewsPost newsPost = new NewsPost(title, author, content);
                        temp.add(newsPost);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                SetListView(temp);
            }
        }
    }
}
