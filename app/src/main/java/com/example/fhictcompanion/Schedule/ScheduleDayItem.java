package com.example.fhictcompanion.Schedule;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class is required to hold all the scheduled lectures and hours
 * for a specific day in the week.
 *
 * These will be supplied on a per week basis for the companion app.
 */
public class ScheduleDayItem implements Serializable {
    private Calendar calendar;
    private List<Lecture> lectures;

    public ScheduleDayItem(Calendar calendar) {
        this.calendar = calendar;
        lectures = new ArrayList<>();
    }

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public int getNumberOfLectures() {
        return lectures.size();
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        return sdf.format(calendar.getTime());
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(calendar.getTime());
    }
}
