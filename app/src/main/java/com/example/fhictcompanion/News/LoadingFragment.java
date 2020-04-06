package com.example.fhictcompanion.News;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fhictcompanion.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView textView;

    private String startMessage;
    private boolean startBar;

    public LoadingFragment(String message, boolean showBar) {
        this.startMessage = message;
        this.startBar = showBar;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loading, container, false);

        progressBar = v.findViewById(R.id.pbLoadItemBar);
        textView = v.findViewById(R.id.tvLoadItemTitle);

        if(startMessage != null){
            SetText(startMessage);
        }
        if(startBar){ShowBar();}
        else {HideBar();}

        return v;
    }

    public void SetText(String text){
        textView.setText(text);
    }

    public void HideBar(){
        progressBar.setVisibility(View.GONE);
    }

    public void ShowBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

}
