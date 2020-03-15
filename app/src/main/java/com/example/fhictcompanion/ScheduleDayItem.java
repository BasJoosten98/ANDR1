package com.example.fhictcompanion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * This class is required to hold all the scheduled lectures and hours
 * for a specific day in the week.
 *
 * These will be supplied on a per week basis for the companion app.
 */
public class ScheduleDayItem implements Serializable {
    private static int MIN_NR_LECTURES = 3;
    private static int MAX_NR_LECTURES = 5;

    private static Random rng = new Random();

    private Date date;
    private int numberOfLectures;
    private List<Lecture> lectures;

    public ScheduleDayItem(Date date) {
        this(date, getRandomNumberOfLectures());

        lectures = new ArrayList<>();
    }

    public ScheduleDayItem(Date date, int numberOfCourses) {
        this.date = date;
        this.numberOfLectures = numberOfCourses;
    }

    public boolean addLecture(String subject, String room, String teacher, TimeDTO start, TimeDTO end) {
        Calendar startsAt = Calendar.getInstance();
        setDateTime(startsAt, start);

        Calendar endsAt = Calendar.getInstance();
        setDateTime(endsAt, end);

        lectures.add(new Lecture(subject, room, teacher, startsAt, endsAt));
        return true;
    }

    private void setDateTime(Calendar dateTime, TimeDTO time) {
        dateTime.setTime(getDate());

        dateTime.set(Calendar.HOUR_OF_DAY, time.hours);
        dateTime.set(Calendar.MINUTE, time.minutes);
    }

    private static int getRandomNumberOfLectures() {
        int shift = MIN_NR_LECTURES;
        int maxInc = MAX_NR_LECTURES - MIN_NR_LECTURES + 1;

        return rng.nextInt(maxInc) + shift;
    }

    public Date getDate() {
        return date;
    }

    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }

    public int getNumberOfLectures() {
        if (lectures.size() == 0) {
            return numberOfLectures;
        }

        return lectures.size();
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getDate());
    }

    public List<Lecture> getLectures() {
        return lectures;
    }
}
