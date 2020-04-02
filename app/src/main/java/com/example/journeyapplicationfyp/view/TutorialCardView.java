package com.example.journeyapplicationfyp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.journeyapplicationfyp.R;

public class TutorialCardView extends CardView {

    private final String LOG_TAG = TutorialCardView.class.getSimpleName();

    private TextView textViewTutorial;

    public TutorialCardView(Context context) {
        super(context);
        init(context);
    }

    public TutorialCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TutorialCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.cardview_tutorial, this);
        textViewTutorial = findViewById(R.id.textview_tutorial);
    }

    public void setTutorial(CharSequence tutorial) {
        textViewTutorial = findViewById(R.id.textview_tutorial);
        textViewTutorial.setText(tutorial);
    }
}
