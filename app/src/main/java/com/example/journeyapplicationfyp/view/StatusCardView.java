package com.example.journeyapplicationfyp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.journeyapplicationfyp.R;

public class StatusCardView extends CardView {

    private final String LOG_TAG = SpinnerCardView.class.getSimpleName();

    private TextView textViewStatusTitle;
    private TextView textViewStatus;

    public StatusCardView(Context context) {
        super(context);

        init(context);
    }

    public StatusCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public StatusCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    /**
     * Initialise custom View.
     * @param context Context.
     */
    public void init(Context context) {
        inflate(context, R.layout.cardview_status, this);

        textViewStatusTitle = findViewById(R.id.textview_status_title);
        textViewStatus = findViewById(R.id.textview_status);
    }

    public void setStatus(String status) {
        textViewStatus = findViewById(R.id.textview_status);
        textViewStatus.setText(status);
    }

    public void setStatusColor(int color) {
        textViewStatusTitle.setBackgroundResource(color);
    }
}


