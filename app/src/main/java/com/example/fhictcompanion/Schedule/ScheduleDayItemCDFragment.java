package com.example.fhictcompanion.Schedule;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fhictcompanion.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleDayItemCDFragment extends Fragment {
    private Lecture lecture;
    private OnFragmentInteractionListener listener;

    public ScheduleDayItemCDFragment() {
        // Required empty public constructor
    }

    public ScheduleDayItemCDFragment(Lecture lecture) {
        this();
        this.lecture = lecture;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_day_item_c_d, container, false);
        setLectureItemDetails(view);

        Button deleteLectureFromScheduleBtn = view.findViewById(R.id.delete_btn);
        deleteLectureFromScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFragmentInteraction(lecture, Action.DELETE);
            }
        });

        return view;
    }

    public void setLectureItemDetails(View view) {
        TextView subject = view.findViewById(R.id.subject);
        subject.setText("Subject: " + lecture.getSubject());

        TextView room = view.findViewById(R.id.room);
        room.setText("Room: " + lecture.getRoom());

        TextView teacher = view.findViewById(R.id.teacher);
        teacher.setText("Teacher: " + lecture.getTeacher());

        TextView timeFrame = view.findViewById(R.id.timeFrame);
        timeFrame.setText(lecture.getTimeFrame());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Lecture lecture, Action action);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "has to implement the OnFragmentInteractionListener interface");
        }
    }
}
