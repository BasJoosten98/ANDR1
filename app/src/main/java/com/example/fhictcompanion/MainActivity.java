package com.example.fhictcompanion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final Date TODAY = new Date();

    private int REQUESTCODE_GET_TOKEN = 1;
    private String fontysToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateDisplay = findViewById(R.id.day_date);

        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE");
        SimpleDateFormat dayOfMonth = new SimpleDateFormat("d");
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        dateDisplay.setText("Today is " + dayOfWeek.format(TODAY) + ", " + dayOfMonth.format(TODAY) + " " + month.format(TODAY) + " " + year.format(TODAY));

        //FINAL (get token)
        Intent intent = new Intent(MainActivity.this, FontysLoginActivity.class);
        startActivityForResult(intent, REQUESTCODE_GET_TOKEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_GET_TOKEN) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("token");
                fontysToken = result;
            }
        }
    }
}
