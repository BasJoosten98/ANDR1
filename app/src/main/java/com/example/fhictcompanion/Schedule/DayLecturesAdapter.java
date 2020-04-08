package com.example.fhictcompanion.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fhictcompanion.R;

import java.util.List;

public class DayLecturesAdapter extends BaseAdapter {
    private List<Lecture> lectures;
    private LayoutInflater inflater;

    public DayLecturesAdapter(Context context, List<Lecture> lectures) {
        this.lectures = lectures;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lectures.size();
    }

    @Override
    public Object getItem(int position) {
        return lectures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lecture_item, parent, false);
        }

        Lecture lecture = (Lecture) getItem(position);
        ((TextView)convertView.findViewById(R.id.subject)).setText("Subject: " + lecture.getSubject());
        ((TextView)convertView.findViewById(R.id.room)).setText("Room: " + lecture.getRoom());
        ((TextView)convertView.findViewById(R.id.teacher)).setText("Teacher: " + lecture.getTeacher());
        ((TextView)convertView.findViewById(R.id.timeFrame)).setText(lecture.getTimeFrame());

        return convertView;
    }
}
