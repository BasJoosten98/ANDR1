package com.example.fhictcompanion.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fhictcompanion.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleDayItemCDFragment extends Fragment {

    public ScheduleDayItemCDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_day_item_c_d, container, false);
    }
}
