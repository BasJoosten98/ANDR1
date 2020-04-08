package com.example.fhictcompanion.Schedule;

import java.io.Serializable;
import java.util.Calendar;

public class Lecture implements Serializable {
    private String subject;
    private String room;
    private String teacher;
    private Calendar startsAt;
    private Calendar endsAt;

    public Lecture(String subject, String room, String teacher, Calendar startsAt, Calendar endsAt) {
        this.subject = subject;
        this.room = room;
        this.teacher = teacher;

        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTimeFrame() {
        return "From " + getStartTime() + " until " + getEndTime();
    }

    private String getStartTime() {
        return formatTime(startsAt);
    }

    private String getEndTime() {
        return formatTime(endsAt);
    }

    private String formatTime(Calendar calendar) {
        return String.format("%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }
}
