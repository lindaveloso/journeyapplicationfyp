package com.example.journeyapplicationfyp.activity;

import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.journeyapplicationfyp.object.StopNameIdMap;

public class FaresActivity extends AppCompatActivity {
    private final String LOG_TAG = FaresActivity.class.getSimpleName();

    private ArrayAdapter<CharSequence> adapterLines;
    private ArrayAdapter<CharSequence> adapterStops;
    private ArrayAdapter<CharSequence> adapterStopsAdults;
    private ArrayAdapter<CharSequence> adapterStopsChildren;
    private ScrollView scrollViewFares;
    private Spinner spinnerFaresLine;
    private Spinner spinnerFaresOrigin;
    private Spinner spinnerFaresDestination;
    private Spinner spinnerFaresAdults;
    private Spinner spinnerFaresChildren;
    private TextView textViewFaresOffPeak;
    private TextView textViewFaresPeak;
    private StopNameIdMap mapStopNameId;
    private String localeDefault;




}
