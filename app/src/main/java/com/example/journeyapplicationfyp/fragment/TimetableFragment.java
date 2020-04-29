package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.MainActivityLogin;


public class TimetableFragment extends Fragment implements View.OnClickListener {

    TextView text2;


    public TimetableFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_timetables, container, false);
        text2 = rootView.findViewById(R.id.text2);
        text2.setOnClickListener(this);
        return rootView;
    }

    public void klik(View rootView) {

        // text2 = rootView.findViewById(R.id.text2);
        // text2.setOnClickListener(this);

        View v = rootView.findViewById(R.id.text2);
        v.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.text2:
                klik(v);
                Intent i = new Intent(requireActivity(), MainActivityLogin.class);
                startActivity(i);
                requireActivity().finish();
                break;
        }

    }
}