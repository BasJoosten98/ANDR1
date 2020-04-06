package com.example.fhictcompanion.News;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fhictcompanion.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private List<NewsPost> newsPosts;
    private ListView listViewNews;

    public NewsFragment(List<NewsPost> list) {
        newsPosts = list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listViewNews = view.findViewById(R.id.lvFontysNews);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item != null){
                    if(item instanceof NewsPost){
                        NewsPost newsPost = (NewsPost)item;
                        Uri websiteUri = Uri.parse(newsPost.getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, websiteUri);
                        startActivity(intent);
                    }
                }
            }
        });
        listViewNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item != null){
                    if(item instanceof NewsPost){
                        NewsPost newsPost = (NewsPost)item;
                        //Uri websiteUri = Uri.parse(newsPost.getLink());
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Fontys News article"); //this will be used as the subject in mails for example
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Fontys News: " + newsPost.getTitle() + " - " + newsPost.getLink());
                        intent.setType("text/plain");
                        startActivity(Intent.createChooser(intent, "Send Fontys news to friend")); //title will be shown in app chooser
                    }
                }
                return true;
            }
        });

        listViewNews.setAdapter(new CustomNewsListAdapter(getContext(), newsPosts));

        return view;
    }

}
