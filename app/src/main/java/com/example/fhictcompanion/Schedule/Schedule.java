package com.example.fhictcompanion.Schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule implements Serializable {
    private int nextDay = 0;
    private List<ScheduleDayItem> scheduleDays = new ArrayList<>();
    private boolean updated;

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

    public void setStartDay(ScheduleDayItem dayItem) {
        List<ScheduleDayItem> scheduleDays = getScheduleDays();

        if (scheduleDays.size() > 0) {
            nextDay = scheduleDays.indexOf(dayItem);
        }
    }

    public ScheduleDayItem getDayAt(int index) {
        List<ScheduleDayItem> scheduleDays = getScheduleDays();

        if (index >= 0 && index < scheduleDays.size()) {
            return scheduleDays.get(index);
        }

        return null;
    }

    public boolean deleteLecture(Lecture lecture) {
        for (ScheduleDayItem scheduleDay: getScheduleDays()) {
            List<Lecture> lectures = scheduleDay.getLectures();

            if (lectures.contains(lecture)) {
                lectures.remove(lecture);
                return true;
            }
        }

        return false;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public ScheduleDayItem dayBelongingTo(Lecture lecture) {
        for (ScheduleDayItem dayItem: getScheduleDays()) {
            if (dayItem.getLectures().contains(lecture)) {
                return dayItem;
            }
        }

        return null;
    }
}
