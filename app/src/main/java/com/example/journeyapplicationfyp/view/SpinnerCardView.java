package com.example.journeyapplicationfyp.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.journeyapplicationfyp.R;

public class SpinnerCardView extends CardView {

    private final String LOG_TAG = SpinnerCardView.class.getSimpleName();
    private ArrayAdapter<CharSequence> adapterStops;
    private Spinner spinnerStops;

    public SpinnerCardView(Context context) {
        super(context);
        init(context);
    }

    public SpinnerCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpinnerCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.cardview_spinner, this);
        spinnerStops = findViewById(R.id.card_view_spinner);

        if (spinnerStops.getBackground().getConstantState() != null) {
            Drawable spinnerDrawable =
                    spinnerStops.getBackground().getConstantState().newDrawable();

            spinnerDrawable.setColorFilter(
                    ContextCompat.getColor(getContext(), R.color.luas_purple),
                    PorterDuff.Mode.SRC_ATOP
            );
            spinnerStops.setBackground(spinnerDrawable);
        }
    }

    private void initAdapterStops(int resArrayStops) {
        adapterStops = ArrayAdapter.createFromResource(
                getContext(), resArrayStops, R.layout.spinner_stops
        );
        adapterStops.setDropDownViewResource(R.layout.spinner_stops);
        spinnerStops.setAdapter(adapterStops);
    }

    public void setLine(String line) {
        final String RED_LINE = "red_line";
        final String GREEN_LINE = "green_line";

        int resArrayStops = 0;

        switch (line) {
            case RED_LINE:
                resArrayStops = R.array.array_stops_redline;

                break;

            case GREEN_LINE:
                resArrayStops = R.array.array_stops_greenline;

                break;

            default:
                Log.wtf(LOG_TAG, "Invalid line specified.");
        }

        initAdapterStops(resArrayStops);
    }

    public Spinner getSpinnerStops() {
        return spinnerStops;
    }

    public void setSelection(String stopName) {
        spinnerStops.setSelection(adapterStops.getPosition(stopName));
    }
}


