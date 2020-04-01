package com.example.fhictcompanion.News;

import android.graphics.drawable.Drawable;
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

public class JSONTaskNewsAmount extends AsyncTask<String, Void, Integer> {

    private String urlString = "https://api.fhict.nl/newsfeeds/Fhict/posts";
    private ITaskReceiver receiver;
    private int requestCode;

    public JSONTaskNewsAmount(ITaskReceiver receiver, int requestCode) {
        this.receiver = receiver;
        this.requestCode = requestCode;
    }


    @Override
    protected Integer doInBackground(String... strings) {
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
                return  jsonArray.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer amount) {
        super.onPostExecute(amount);
        if(receiver == null){return;}
        receiver.OnTaskReceived(amount, requestCode);
    }
}
