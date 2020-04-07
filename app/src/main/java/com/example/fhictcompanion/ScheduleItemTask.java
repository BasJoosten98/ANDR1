package com.example.fhictcompanion;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

class ScheduleItemTask extends AsyncTask<String, Void, String> {
    private IScheduleContext context;

    public ScheduleItemTask(IScheduleContext context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseText = "";

        try {
            URL url = new URL("https://api.fhict.nl/schedule/me?expandTeacher=false&days=5&startLastMonday=true");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + strings[0]);
            connection.connect();

            InputStream is = connection.getInputStream();
            Scanner scn = new Scanner(is);

            responseText = scn.useDelimiter("\\Z").next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseText;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Schedule schedule = new Schedule();;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonScheduleItem = jsonArray.getJSONObject(i);

                String subject = jsonScheduleItem.getString("subject");
                String room = jsonScheduleItem.getString("room");
                String teacher = jsonScheduleItem.getString("teacherAbbreviation");
                String startDateTime = jsonScheduleItem.getString("start");
                String endDateTime = jsonScheduleItem.getString("end");

                schedule.addLecture(subject, room, teacher, asCalendar(startDateTime), asCalendar(endDateTime));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        context.setScheduleItems(schedule);
    }

    private Calendar asCalendar(String ISODateTime) {
        String[] dateTime = ISODateTime.split("T");
        Date date = null;
        Calendar calendar = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTime[0].concat(" " + dateTime[1]));
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}
