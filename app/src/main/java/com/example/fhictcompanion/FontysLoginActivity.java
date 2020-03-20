package com.example.fhictcompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class FontysLoginActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fontys_login);

        //Add or replace fragment in container
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        TokenFragment fragment = new TokenFragment();
        fragTrans.add(R.id.lyFontysTokenFragment, fragment, "MyFrag");
        fragTrans.commit();
    }

    @Override
    public void onFragmentInteraction(String token) {
        if(token != null){
            Intent intent = new Intent();
            intent.putExtra("token", token);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
