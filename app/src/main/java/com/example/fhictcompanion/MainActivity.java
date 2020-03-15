package com.example.fhictcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final Date TODAY = new Date();
    private static final int NEXT_DAY = 1;

    private Calendar calendar;
    private List<ScheduleDayItem> scheduleItems;

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
    }

    public void viewSchedule(View view) {
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);

        // Pass the first schedule item (today)
        // Later add functionality to go next day and back.
        ScheduleDayItem today = scheduleItems.get(0);
        scheduleIntent.putExtra("todaysScheduleItem", today);

        startActivity(scheduleIntent);
    }
}
