package com.example.journeyapplicationfyp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.util.SessionManager;

public class TimetableFragment extends Fragment implements View.OnClickListener {


    public static final String mypreference = "mypref";
    TextView text2;
    SharedPreferences sharedpreferences;
    SessionManager sessionManager;


    public TimetableFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_timetables, container, false);
        text2 = rootView.findViewById(R.id.text2);
        text2.setOnClickListener(this);
        sessionManager = new SessionManager();
        text2.setText(sessionManager.getFavourties());

        return rootView;
    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.text2:

            case R.id.add:
                Toast.makeText(requireActivity(), "Added to Favourites", Toast.LENGTH_SHORT).show();

        }

    }
}