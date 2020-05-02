package com.example.journeyapplicationfyp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.journeyapplicationfyp.R;

public class StopForecastCardView extends CardView {

    private TextView textViewDirection;
    private TableRow[] tableRowStops;
    private TextView[] textViewStopNames;
    private TextView[] textViewStopTimes;

    public StopForecastCardView(Context context) {
        super(context);
        init(context);
    }

    public StopForecastCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StopForecastCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = inflate(context, R.layout.cardview_stop_forecast, this);
        textViewDirection = findViewById(R.id.textview_direction);
        tableRowStops = new TableRow[]{
                findViewById(R.id.tablerow_stop1),
                findViewById(R.id.tablerow_stop2),
                findViewById(R.id.tablerow_stop3),
                findViewById(R.id.tablerow_stop4),
                findViewById(R.id.tablerow_stop5),
                findViewById(R.id.tablerow_stop6)
        };
        textViewStopNames = new TextView[]{
                findViewById(R.id.textview_stop1_name),
                findViewById(R.id.textview_stop2_name),
                findViewById(R.id.textview_stop3_name),
                findViewById(R.id.textview_stop4_name),
                findViewById(R.id.textview_stop5_name),
                findViewById(R.id.textview_stop6_name)
        };
        textViewStopTimes = new TextView[]{
                findViewById(R.id.textview_stop1_time),
                findViewById(R.id.textview_stop2_time),
                findViewById(R.id.textview_stop3_time),
                findViewById(R.id.textview_stop4_time),
                findViewById(R.id.textview_stop5_time),
                findViewById(R.id.textview_stop6_time)
        };
    }

    public void clearStopForecast() {
        for (int i = 0; i < 6; i++) {
            textViewStopNames[i].setText("");
            textViewStopTimes[i].setText("");

            textViewStopNames[i].setText("");
            textViewStopTimes[i].setText("");
        }
    }

    public void setStopForecastDirection(String direction) {
        textViewDirection.setText(direction);
    }

    public void setNoTramsForecast() {
        textViewStopNames[0].setText(R.string.zerotrams);
    }

    public void setStopNames(int index, String tram) {
        textViewStopNames[index].setText(tram);
    }

    public void setStopTimes(int index, String dueMinutes) {
        textViewStopTimes[index].setText(dueMinutes);
    }
}
