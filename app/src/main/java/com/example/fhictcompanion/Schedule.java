package com.example.fhictcompanion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule implements Serializable {
    private int nextDay = 0;
    private List<ScheduleDayItem> scheduleDays = new ArrayList<>();

    public void addLecture(String subject, String room, String teacher, Calendar startsAt, Calendar endsAt) {
        ScheduleDayItem matchingDay = findDay(startsAt);

        if (matchingDay == null) {
            matchingDay = new ScheduleDayItem(startsAt);
            scheduleDays.add(matchingDay);
        }

        matchingDay.addLecture(new Lecture(subject, room, teacher, startsAt, endsAt));
    }

    public ScheduleDayItem findDay(Calendar calendar) {
        for (ScheduleDayItem scheduleDay: scheduleDays) {
            if (equalDates(scheduleDay.getCalendar(), calendar)) {
                return scheduleDay;
            }
        }

        return null;
    }

    public List<ScheduleDayItem> getScheduleDays() {
        return new ArrayList<>(scheduleDays);
    }

    private boolean equalDates(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    public ScheduleDayItem getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return findDay(calendar);
    }

    public ScheduleDayItem getNextDay() {
        int numberOfDays = getScheduleDays().size();

        if (numberOfDays > 0) {
            ScheduleDayItem day = scheduleDays.get(nextDay % numberOfDays);
            nextDay++;

            return day;
        }

        return null;
    }
}
