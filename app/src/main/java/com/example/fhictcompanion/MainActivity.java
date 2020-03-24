package com.example.fhictcompanion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IPersonContext, IScheduleContext {
    private static final Date TODAY = new Date();

    private Schedule schedule;

    private int REQUESTCODE_GET_TOKEN = 1;
    private String fontysToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTodaysDate();

        Button btnReadNews = findViewById(R.id.read_news_button);
        btnReadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("token", fontysToken);
                startActivity(intent);
            }
        });

        // Alternatief was geweest om de login activity als eerst te starten en na succesvolle login
        // een main activity aan te maken met token als extra.

        // FINAL (get token)
        Intent intent = new Intent(MainActivity.this, FontysLoginActivity.class);
        startActivityForResult(intent, REQUESTCODE_GET_TOKEN);
    }

    private void displayTodaysDate() {
        TextView dateDisplay = findViewById(R.id.day_date);
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE");
        SimpleDateFormat dayOfMonth = new SimpleDateFormat("d");
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        dateDisplay.setText("Today is " + dayOfWeek.format(TODAY) + ", " + dayOfMonth.format(TODAY) + " " + month.format(TODAY) + " " + year.format(TODAY));
    }

    public void viewSchedule(View view) {
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);

        ScheduleDayItem today = schedule.getToday();
        scheduleIntent.putExtra("todaysScheduleItem", today);

        startActivity(scheduleIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_GET_TOKEN) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("token");
                fontysToken = result;

                // Used to copy token for testing in postman.
                System.out.println(fontysToken);

                // Get person's information
                PersonTask personTask = new PersonTask(this);
                personTask.execute(fontysToken);

                // Get all the schedule items per day.
                ScheduleItemTask scheduleItemTask = new ScheduleItemTask(this);
                scheduleItemTask.execute(fontysToken);
            }
        }
    }

    @Override
    public void setPersonDetails(Person person) {
        TextView welcomeText = findViewById(R.id.welcome);
        welcomeText.setText("Welcome " + person.toString() + "!");
    }

    @Override
    public void setScheduleItems(Schedule schedule) {
        this.schedule = schedule;
        ListView lv = findViewById(R.id.schedule_days);
        lv.setAdapter(new ScheduleDayAdapter(this, schedule.getScheduleDays()));
    }
}
