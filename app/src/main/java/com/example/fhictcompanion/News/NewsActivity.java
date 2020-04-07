package com.example.fhictcompanion.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fhictcompanion.R;
import com.example.fhictcompanion.TokenFragment;

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

    //private ProgressDialog progressDialog;
    private String fontysToken;
    private LoadingFragment loadingFragment;

    private int REQUESTCODE_GET_NEWS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //GET EXTRA's FROM SENDER
        Intent sender = getIntent();
        fontysToken = sender.getStringExtra("token");
        Parcelable parcelables[] = sender.getParcelableArrayExtra("posts");

        if(parcelables != null) { //IF EXTRA's CONTAINS POSTS, USE THEM!

            //TRANSLATE PARCELABLES TO POSTS
            List<NewsPost> newsPosts = new ArrayList<>();
            for (int i = 0; i < parcelables.length; i++) {
                NewsPost post = (NewsPost) parcelables[i];
                post.DownloadImage();
                newsPosts.add(post);
            }

            //Add or replace fragment in container
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragManager.beginTransaction();
            NewsFragment fragment = new NewsFragment(newsPosts);
            fragTrans.add(R.id.layoutNews, fragment, "MyFrag");
            fragTrans.commit();

        }
        else { //ELSE RETRIEVE POSTS FROM THE FONTYS API

            //Add or replace fragment in container
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragManager.beginTransaction();
            LoadingFragment fragment = new LoadingFragment("Retrieving Fontys news...", true);
            loadingFragment = fragment;
            fragTrans.add(R.id.layoutNews, fragment, "MyFrag");
            fragTrans.commit();

            /* //SHOW DIALOG FOR THAT THE POSTS ARE BEING RETRIEVED
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Retrieving Fontys news...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
             */

            loadNews();
        }
    }

    private void loadNews(){
        new JSONTaskNews(this, REQUESTCODE_GET_NEWS).execute(fontysToken);
    }

    @Override
    public void OnTaskReceived(Object data, int requestCode) {
        if(REQUESTCODE_GET_NEWS == requestCode){
            if(data != null){
                if(data instanceof ArrayList){
                    ArrayList<NewsPost> newsPosts = (ArrayList<NewsPost>)data;

                    //Add or replace fragment in container
                    FragmentManager fragManager = getSupportFragmentManager();
                    FragmentTransaction fragTrans = fragManager.beginTransaction();
                    NewsFragment fragment = new NewsFragment(newsPosts);
                    fragTrans.replace(R.id.layoutNews, fragment, "MyFrag");
                    fragTrans.commit();

                    return;
                    //progressDialog.dismiss();
                }
            }
            loadingFragment.SetText("Retrieving Fontys news failed!");
            loadingFragment.HideBar();

        }
    }
}
