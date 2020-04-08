package com.example.fhictcompanion.News;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

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

public class JSONTaskNews extends AsyncTask<String, Void, List<NewsPost>> {

    private String urlString = "https://api.fhict.nl/newsfeeds/Fhict/posts";
    private ITaskReceiver receiver;
    private int requestCode;

    public JSONTaskNews(ITaskReceiver receiver, int requestCode) {
        this.receiver = receiver;
        this.requestCode = requestCode;
    }


    @Override
    protected List<NewsPost> doInBackground(String... strings) {
        if(strings.length <= 0){return  null;} //no URL, token given
        String token = strings[0];

        List<NewsPost> result = new ArrayList<NewsPost>();

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
            String jsonString = scanner.useDelimiter("\\Z").next();

            if(jsonString != null){
                JSONArray jsonArray = new JSONArray(jsonString);
                for(int i = 0; i < jsonArray.length(); i++) {

                    JSONObject news = jsonArray.getJSONObject(i);

                    String title = news.getString("title");
                    String author = news.getString("author");
                    String content = news.getString("content");
                    String link = news.getString("link");

                    String imageUrl = null;
                    if(news.has("image")){imageUrl = news.getString("image");}
                    else if(news.has("thumbnail")){imageUrl = news.getString("thumbnail");}

                    Bitmap image = null;
                    if(imageUrl != null){
                        try {
                            URL imgUrl = new URL(imageUrl);
                            HttpURLConnection connectionImage = (HttpURLConnection)imgUrl.openConnection();
                            connectionImage.connect();
                            InputStream isImage = connectionImage.getInputStream();
                            image = BitmapFactory.decodeStream(isImage);
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    NewsPost newsPost = new NewsPost(title, author, content, link, image, imageUrl);
                    result.add(newsPost);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<NewsPost> list) {
        super.onPostExecute(list);
        if(receiver == null){return;}
        receiver.OnTaskReceived(list, requestCode);
    }
}
