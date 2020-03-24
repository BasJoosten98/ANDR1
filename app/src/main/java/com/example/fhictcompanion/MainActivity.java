package com.example.fhictcompanion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IPersonContext {
    private static final Date TODAY = new Date();
    private static final int NEXT_DAY = 1;

    private Calendar calendar;
    private List<ScheduleDayItem> scheduleItems;

    private int REQUESTCODE_GET_TOKEN = 1;
    private String fontysToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTodaysDate();

        calendar = Calendar.getInstance();
        calendar.setTime(TODAY);

        ScheduleDayItem day1 = new ScheduleDayItem(calendar.getTime());

        day1.addLecture(
                "SoT",
                "R1_2.79",
                "srk",
                new TimeDTO(10, 30),
                new TimeDTO(12, 00)
        );

        day1.addLecture(
                "pes",
                "R1_2.40",
                "pes",
                new TimeDTO(12, 45),
                new TimeDTO(14, 15)
        );

        calendar.add(Calendar.DAY_OF_MONTH, NEXT_DAY);
        ScheduleDayItem day2 = new ScheduleDayItem(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, NEXT_DAY);
        ScheduleDayItem day3 = new ScheduleDayItem(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, NEXT_DAY);
        ScheduleDayItem day4 = new ScheduleDayItem(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, NEXT_DAY);
        ScheduleDayItem day5 = new ScheduleDayItem(calendar.getTime());

        scheduleItems = new ArrayList<>();

        scheduleItems.add(day1);
        scheduleItems.add(day2);
        scheduleItems.add(day3);
        scheduleItems.add(day4);
        scheduleItems.add(day5);

        ListView lv = findViewById(R.id.schedule_days);
        lv.setAdapter(new ScheduleDayAdapter(this, scheduleItems));

        Button btnReadNews = findViewById(R.id.read_news_button);
        btnReadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("token", fontysToken);
                startActivity(intent);
            }
        });

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

        // Pass the first schedule item (today)
        // Later add functionality to go next day and back.
        ScheduleDayItem today = scheduleItems.get(0);
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

                // Get person's information
                PersonTask personTask = new PersonTask(this);
                personTask.execute(fontysToken);
            }
        }
    }

    @Override
    public void SetPersonDetails(Person person) {
        TextView welcomeText = findViewById(R.id.welcome);
        welcomeText.setText("Welcome " + person.toString() + "!");
    }
}
