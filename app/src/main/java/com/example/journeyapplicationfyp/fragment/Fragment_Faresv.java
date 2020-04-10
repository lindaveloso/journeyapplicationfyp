package com.example.journeyapplicationfyp.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.api.ApiFares;
import com.example.journeyapplicationfyp.api.ApiMethods;
import com.example.journeyapplicationfyp.object.StopNameIdMap;

import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Fragment_Faresv extends Fragment {
    private final String LOG_TAG = Fragment_Faresv.class.getSimpleName();

    private ArrayAdapter<CharSequence> adapterLines;
    private ArrayAdapter<CharSequence> adapterStops;
    private ArrayAdapter<CharSequence> adapterStopsAdults;
    private ArrayAdapter<CharSequence> adapterStopsChildren;
    private ScrollView scrollViewFares;
    private ProgressBar progressbar_fares;
    private Spinner spinnerFaresLine;
    private Spinner spinnerFaresOrigin;
    private Spinner spinnerFaresDestination;
    private Spinner spinnerFaresAdults;
    private Spinner spinnerFaresChildren;
    private TextView textViewFaresOffPeak;
    private TextView textViewFaresPeak;
    private StopNameIdMap mapStopNameId;
    private String localeDefault;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tram2, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(getActivity(),
                            R.color.system)
            );
        }

        faresinit();
    }


    private void faresinit() {
        localeDefault = Locale.getDefault().toString();

        /* Instantiate a new StopNameIdMap. */
        mapStopNameId = new StopNameIdMap(localeDefault);

        scrollViewFares = getActivity().findViewById(R.id.scrollview_fares);
        spinnerFaresLine = getActivity().findViewById(R.id.spinner_fares_line);

        spinnerFaresLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                       long l) {
                int resArrayStops = 0;

                if (position == 0) {
                    resArrayStops = R.array.array_stops_redline;
                } else if (position == 1) {
                    resArrayStops = R.array.array_stops_greenline;
                } else {
                    Log.wtf(LOG_TAG, "Spinner position not 0 or 1. Setting to Red Line.");
                }

                adapterStops = ArrayAdapter.createFromResource(
                        getActivity(),
                        resArrayStops,
                        R.layout.spinner_stops
                );

                spinnerFaresOrigin.setAdapter(adapterStops);
                spinnerFaresDestination.setAdapter(adapterStops);

                /* If the user changes line, reset the displayed fares. */
                clearCalculatedFares();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerFaresOrigin = getActivity().findViewById(R.id.spinner_fares_origin);

        spinnerFaresOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadFaresBasedOnSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerFaresDestination = getActivity().findViewById(R.id.spinner_fares_destination);

        spinnerFaresDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadFaresBasedOnSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerFaresAdults = getActivity().findViewById(R.id.spinner_fares_adults);
        spinnerFaresAdults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadFaresBasedOnSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerFaresChildren = getActivity().findViewById(R.id.spinner_fares_children);

        spinnerFaresChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadFaresBasedOnSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        setSpinnerColor(spinnerFaresLine);
        setSpinnerColor(spinnerFaresOrigin);
        setSpinnerColor(spinnerFaresDestination);
        setSpinnerColor(spinnerFaresAdults);
        setSpinnerColor(spinnerFaresChildren);

        adapterLines = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.array_lines,
                R.layout.spinner_stops
        );
        adapterStopsAdults = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.array_number_pax,
                R.layout.spinner_stops
        );
        adapterStopsChildren = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.array_number_pax,
                R.layout.spinner_stops
        );

        adapterLines.setDropDownViewResource(R.layout.spinner_stops);
        adapterStopsAdults.setDropDownViewResource(R.layout.spinner_stops);
        adapterStopsChildren.setDropDownViewResource(R.layout.spinner_stops);

        spinnerFaresLine.setAdapter(adapterLines);
        spinnerFaresAdults.setAdapter(adapterStopsAdults);
        spinnerFaresChildren.setAdapter(adapterStopsChildren);

        /* Start with a default of 1 adult. */
        spinnerFaresAdults.setSelection(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        setIsLoading(false);
        textViewFaresOffPeak = getActivity().findViewById(R.id.textview_fares_offpeak);
        textViewFaresPeak = getActivity().findViewById(R.id.textview_fares_peak);

    }

    /**
     * Utility method to load calculated fares based on the current values of all Spinners.
     */
    private void loadFaresBasedOnSpinnerSelected() {
        if (spinnerFaresOrigin.getSelectedItem().toString().equals(
                getString(R.string.select_a_stop))
                ||
                spinnerFaresDestination.getSelectedItem().toString().equals(
                        getString(R.string.select_a_stop))) {
            return;
        }

        setIsLoading(true);

        final String API_URL = "https://api.thecosmicfrog.org/cgi-bin";
        final String API_ACTION = "farecalc";

        /*
         * Prepare Retrofit API call.
         */
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        ApiMethods methods = restAdapter.create(ApiMethods.class);

        Callback<ApiFares> callback = new Callback<ApiFares>() {
            @Override
            public void success(ApiFares apiFares, Response response) {
                String fareOffPeak = apiFares.getOffpeak();
                String farePeak = apiFares.getPeak();

                textViewFaresOffPeak.setText("€" + fareOffPeak);
                textViewFaresPeak.setText("€" + farePeak);

                /*
                 * Now that we've got the fare values, scroll down to ensure the fares and fare
                 * disclaimer is displayed to the user.
                 */
                if (scrollViewFares != null) {
                    scrollViewFares.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollViewFares.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }

                setIsLoading(false);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                setIsLoading(false);

                clearCalculatedFares();

                Toast.makeText(
                        getActivity(),
                        getString(R.string.message_error),
                        Toast.LENGTH_LONG
                ).show();

                Log.e(LOG_TAG, "Failure during call to server.");

                /*
                 * If we get a message or a response from the server, there's likely an issue with
                 * the client request or the server's response itself.
                 */
                if (retrofitError.getMessage() != null) {
                    Log.e(LOG_TAG, retrofitError.getMessage());
                }

                if (retrofitError.getResponse() != null) {
                    if (retrofitError.getResponse().getUrl() != null) {
                        Log.e(LOG_TAG, retrofitError.getResponse().getUrl());
                    }

                    Log.e(LOG_TAG, Integer.toString(retrofitError.getResponse().getStatus()));

                    if (retrofitError.getResponse().getHeaders() != null) {
                        Log.e(LOG_TAG, retrofitError.getResponse().getHeaders().toString());
                    }

                    if (retrofitError.getResponse().getBody() != null) {
                        Log.e(LOG_TAG, retrofitError.getResponse().getBody().toString());
                    }

                    if (retrofitError.getResponse().getReason() != null) {
                        Log.e(LOG_TAG, retrofitError.getResponse().getReason());
                    }
                }

                /*
                 * If we don't receive a message or response, we can still get an idea of what's
                 * going on by getting the "kind" of error.
                 */
                if (retrofitError.getKind() != null) {
                    Log.e(LOG_TAG, retrofitError.getKind().toString());
                }
            }
        };

        /* Call API and get fares from server. */
        methods.getFares(
                API_ACTION,
                mapStopNameId.get(spinnerFaresOrigin.getSelectedItem().toString()),
                mapStopNameId.get(spinnerFaresDestination.getSelectedItem().toString()),
                spinnerFaresAdults.getSelectedItem().toString(),
                spinnerFaresChildren.getSelectedItem().toString(),
                callback
        );
    }


    private void clearCalculatedFares() {
        textViewFaresOffPeak.setText(getString(R.string.default_amount));
        textViewFaresPeak.setText(getString(R.string.default_amount));
    }


    private void setIsLoading(final boolean loading) {
        progressbar_fares = getActivity().findViewById(R.id.progressbar_fares);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading) {
                    progressbar_fares.setVisibility(View.VISIBLE);
                } else {
                    progressbar_fares.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * Cosmetic method to set the Spinner's arrow to purple.
     *
     * @param spinner Spinner
     */
    private void setSpinnerColor(Spinner spinner) {
        /* Set the Spinner's colour to Luas purple. */
        if (spinner.getBackground().getConstantState() != null) {
            Drawable spinnerDrawable =
                    spinner.getBackground().getConstantState().newDrawable();

            spinnerDrawable.setColorFilter(
                    ContextCompat.getColor(getActivity(), R.color.luas_purple),
                    PorterDuff.Mode.SRC_ATOP
            );

            spinner.setBackground(spinnerDrawable);
        }
    }
}




