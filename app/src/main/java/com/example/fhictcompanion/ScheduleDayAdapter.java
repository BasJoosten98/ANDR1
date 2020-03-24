package com.example.fhictcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScheduleDayAdapter extends BaseAdapter {
    private List<ScheduleDayItem> scheduleDays;
    private Context context;
    private LayoutInflater li;

    public ScheduleDayAdapter(Context context, List<ScheduleDayItem> scheduleDays) {
        this.context = context;
        this.scheduleDays = scheduleDays;

        li = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scheduleDays.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = li.inflate(R.layout.day_item, parent, false);
        }

        ScheduleDayItem item = scheduleDays.get(position);

        TextView day = convertView.findViewById(R.id.day);
        TextView date = convertView.findViewById(R.id.date);
        TextView nrOfActivities = convertView.findViewById(R.id.number_of_activities);

        day.setText(item.getDay());
        date.setText(item.toString());
        nrOfActivities.setText(item.getNumberOfLectures() + " lectures scheduled");

        return convertView;
    }
}
