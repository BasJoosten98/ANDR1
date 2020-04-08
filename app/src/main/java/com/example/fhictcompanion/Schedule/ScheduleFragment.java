package com.example.fhictcompanion.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fhictcompanion.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private ScheduleDayItem scheduleDay;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public ScheduleFragment(ScheduleDayItem scheduleDay) {
        this();
        this.scheduleDay = scheduleDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        ListView lv = view.findViewById(R.id.lecture_list);
        lv.setAdapter(new DayLecturesAdapter(getContext(), scheduleDay.getLectures()));

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                ScheduleDayItemCDFragment fragment = new ScheduleDayItemCDFragment(scheduleDay.getLectures().get(position));
                transaction.replace(R.id.schedule_fragment_container, fragment, "schedule_frag");
                transaction.commit();
                return false;
            }
        });

        return view;
    }
}
