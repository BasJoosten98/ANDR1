package com.example.fhictcompanion.News;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class JSONTaskBitmap extends AsyncTask<String, Void, Bitmap> {

    private ITaskReceiver receiver;
    private int requestCode;

    public JSONTaskBitmap(ITaskReceiver receiver, int requestCode) {
        this.receiver = receiver;
        this.requestCode = requestCode;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        if(strings.length <= 0){return  null;} //no URL given
        String imageUrl = strings[0];

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
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(receiver == null){return;}
        receiver.OnTaskReceived(bitmap, requestCode);
    }
}
