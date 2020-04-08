package com.example.fhictcompanion.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fhictcompanion.R;

public class ScheduleActivity extends AppCompatActivity {
    Schedule schedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("schedule");

        nextDay(null);
    }

    public void home(View view) {
        this.onBackPressed();
    }

    public void nextDay(View view) {
        ScheduleDayItem scheduleDay = schedule.getNextDay();

        TextView date = findViewById(R.id.day_date);
        TextView dayOfTheWeek = findViewById(R.id.day_of_the_week);
        date.setText(scheduleDay.toString());
        dayOfTheWeek.setText(scheduleDay.getDay());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ScheduleFragment fragment = new ScheduleFragment(scheduleDay);
        transaction.replace(R.id.schedule_fragment_container, fragment, "schedule_frag");
        transaction.commit();
    }
}
