package com.example.fhictcompanion.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fhictcompanion.R;

import static com.example.fhictcompanion.MainActivity.schedule;

public class ScheduleActivity extends AppCompatActivity implements ScheduleDayItemCDFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        nextDay(null);
    }

    public void home(View view) {
        this.onBackPressed();
    }

    public void nextDay(View view) {
        ScheduleDayItem scheduleDay = schedule.getNextDay();

        displayScheduleDay(scheduleDay);
    }

    private void displayScheduleDay(ScheduleDayItem scheduleDay) {
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

    @Override
    public void onFragmentInteraction(Lecture lecture, Action action) {
        // Code and lecture.
        switch (action) {
            case DELETE:
                ScheduleDayItem day = schedule.dayBelongingTo(lecture);

                schedule.deleteLecture(lecture);
                // Flag is used such that main activity knows the schedule changed
                // and should update the overview provided by the list view.
                schedule.setUpdated(true);

                displayScheduleDay(day);
                break;
            default:
                break;
        }
    }
}
