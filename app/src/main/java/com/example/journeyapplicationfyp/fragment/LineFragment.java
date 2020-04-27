package com.example.journeyapplicationfyp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.api.ApiMethods;
import com.example.journeyapplicationfyp.api.ApiTimes;
import com.example.journeyapplicationfyp.object.EnglishGaeilgeMap;
import com.example.journeyapplicationfyp.object.StopForecast;
import com.example.journeyapplicationfyp.object.StopNameIdMap;
import com.example.journeyapplicationfyp.util.Preferences;
import com.example.journeyapplicationfyp.util.StopForecastUtil;
import com.example.journeyapplicationfyp.view.SpinnerCardView;
import com.example.journeyapplicationfyp.view.StopForecastCardView;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;



public class LineFragment extends Fragment {

    private static int resLayoutFragmentLine;
    private static int resSpinnerCardView;
    private static int resInboundStopForecastCardView;
    private static int resOutboundStopForecastCardView;
    private static int resArrayStopsRedLine;
    private static int resArrayStopsGreenLine;
    private static StopNameIdMap mapStopNameId;
    private static String localeDefault;
    private final String LOG_TAG = LineFragment.class.getSimpleName();
    private Context context;
    private View rootView = null;
    private TabLayout tabLayout;
    private SpinnerCardView spinnerCardView;
    private StopForecastCardView inboundStopForecastCardView;
    private StopForecastCardView outboundStopForecastCardView;
    private boolean isInitialised;
    private TimerTask timerTaskReload;
    private boolean shouldAutoReload = false;
    private String line;
    private boolean isVisibleToUser = false;

    public LineFragment() {
    }

    public static LineFragment newInstance(String line) {
        LineFragment lineFragment = new LineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.RES_ARRAY_STOPS_RED_LINE, R.array.array_stops_redline);
        bundle.putInt(Constant.RES_ARRAY_STOPS_GREEN_LINE, R.array.array_stops_greenline);

        switch (line) {
            case Constant.RED_LINE:
                bundle.putString(Constant.LINE, Constant.RED_LINE);
                bundle.putInt(Constant.RES_LAYOUT_FRAGMENT_LINE, R.layout.tab_redline);
                bundle.putInt(Constant.RES_SPINNER_CARDVIEW, R.id.redline_spinner_card_view);
                bundle.putInt(Constant.RES_SCROLLVIEW, R.id.redline_scrollview);
                bundle.putInt(
                        Constant.RES_INBOUND_STOPFORECASTCARDVIEW,
                        R.id.redline_inbound_stopforecastcardview
                );
                bundle.putInt(
                        Constant.RES_OUTBOUND_STOPFORECASTCARDVIEW,
                        R.id.redline_outbound_stopforecastcardview
                );

                break;
            case Constant.GREEN_LINE:
                bundle.putString(Constant.LINE, Constant.GREEN_LINE);
                bundle.putInt(Constant.RES_LAYOUT_FRAGMENT_LINE, R.layout.tab_greenline);
                bundle.putInt(Constant.RES_SPINNER_CARDVIEW, R.id.greenline_spinner_card_view);
                bundle.putInt(Constant.RES_SWIPEREFRESHLAYOUT, R.id.greenline_swiperefreshlayout);
                bundle.putInt(Constant.RES_SCROLLVIEW, R.id.greenline_scrollview);
                bundle.putInt(
                        Constant.RES_INBOUND_STOPFORECASTCARDVIEW,
                        R.id.greenline_inbound_stopforecastcardview
                );
                bundle.putInt(
                        Constant.RES_OUTBOUND_STOPFORECASTCARDVIEW,
                        R.id.greenline_outbound_stopforecastcardview
                );

                break;

            default:
                Log.wtf(LineFragment.class.getSimpleName(), "Invalid line specified.");
        }
        lineFragment.setArguments(bundle);
        return lineFragment;
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        context = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragmentVariables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(resLayoutFragmentLine, container, false);
        localeDefault = Locale.getDefault().toString();
        mapStopNameId = new StopNameIdMap(localeDefault);
        if (isAdded()) {
            isInitialised = initFragment();
        }
        if (!getActivity().getIntent().hasExtra(Constant.STOP_NAME)) {
            if (Preferences.selectedStopName(context, Constant.NO_LINE) != null) {
                String stopName = Preferences.selectedStopName(context, Constant.NO_LINE);
                setTabAndSpinner(stopName);
            }
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.isVisibleToUser = isVisibleToUser;

        if (isInitialised) {
            if (spinnerCardView.getSpinnerStops().getSelectedItemPosition() == 0) {
                Log.i(LOG_TAG, "Spinner selected item is \"Select a stop...\"");
                return;
            }

            /* When this tab is visible to the user, load a stop forecast. */
            if (isVisibleToUser) {
                if (spinnerCardView.getSpinnerStops().getSelectedItem() != null) {
                    String stopName =
                            spinnerCardView.getSpinnerStops().getSelectedItem().toString();

                    Preferences.saveSelectedStopName(context, Constant.NO_LINE, stopName);

                    loadStopForecast(stopName, false);

                    shouldAutoReload = true;
                } else {
                    Log.w(LOG_TAG, "Spinner selected item is null.");
                }
            } else {
                shouldAutoReload = false;
            }
        }
    }

    private void initFragmentVariables() {
        resArrayStopsRedLine = getArguments().getInt(Constant.RES_ARRAY_STOPS_RED_LINE);
        resArrayStopsGreenLine = getArguments().getInt(Constant.RES_ARRAY_STOPS_GREEN_LINE);
        line = getArguments().getString(Constant.LINE);
        resLayoutFragmentLine = getArguments().getInt(Constant.RES_LAYOUT_FRAGMENT_LINE);
        resSpinnerCardView = getArguments().getInt(Constant.RES_SPINNER_CARDVIEW);
        resInboundStopForecastCardView =
                getArguments().getInt(Constant.RES_INBOUND_STOPFORECASTCARDVIEW);
        resOutboundStopForecastCardView =
                getArguments().getInt(Constant.RES_OUTBOUND_STOPFORECASTCARDVIEW);
    }

    private boolean initFragment() {
        tabLayout = getActivity().findViewById(R.id.tablayout);
        // setIsLoading(true);
        spinnerCardView = rootView.findViewById(resSpinnerCardView);
        spinnerCardView.setLine(line);

        spinnerCardView.getSpinnerStops().setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {

                        if (isVisibleToUser) {
                            if (position == 0) {
                                shouldAutoReload = true;
                                return;
                            }

                            shouldAutoReload = true;
                            String selectedStopName =
                                    spinnerCardView
                                            .getSpinnerStops().getItemAtPosition(position).toString();

                            loadStopForecast(selectedStopName, false);

                            if (isVisibleToUser) {
                                Preferences.saveSelectedStopName(
                                        context,
                                        line,
                                        selectedStopName
                                );
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        inboundStopForecastCardView =
                rootView.findViewById(
                        resInboundStopForecastCardView
                );
        inboundStopForecastCardView.setStopForecastDirection(
                getString(R.string.inbound)
        );

        outboundStopForecastCardView =
                rootView.findViewById(
                        resOutboundStopForecastCardView
                );
        outboundStopForecastCardView.setStopForecastDirection(
                getString(R.string.outbound)
        );

        return true;
    }

    private boolean setTabAndSpinner(String stopName) {
        String[] arrayStopsRedLine = getResources().getStringArray(
                resArrayStopsRedLine
        );
        String[] arrayStopsGreenLine = getResources().getStringArray(
                resArrayStopsGreenLine
        );

        List<String> listStopsRedLine = Arrays.asList(arrayStopsRedLine);
        List<String> listStopsGreenLine = Arrays.asList(arrayStopsGreenLine);
        List<String> listStopsThisLine = null;

        int indexOtherLine = -1;

        switch (line) {
            case Constant.RED_LINE:
                listStopsThisLine = listStopsRedLine;
                indexOtherLine = 1;

                break;

            case Constant.GREEN_LINE:
                listStopsThisLine = listStopsGreenLine;
                indexOtherLine = 0;

                break;

            default:
                Log.wtf(LOG_TAG, "Invalid line specified.");
        }

        if (listStopsThisLine == null) {
            Log.e(LOG_TAG, "List of stops for this line is null.");

            return false;
        }

        if (listStopsThisLine.contains(stopName)) {
            spinnerCardView.setSelection(stopName);

            return true;
        } else {
            TabLayout.Tab tab = tabLayout.getTabAt(indexOtherLine);

            if (tab != null) {
                tab.select();
            }

            spinnerCardView.setSelection(Preferences.selectedStopName(context, line));

            return false;
        }
    }

    private void clearStopForecast() {
        inboundStopForecastCardView.clearStopForecast();
        outboundStopForecastCardView.clearStopForecast();
    }

    public void autoReloadStopForecast(int delayTimeMillis) {
        final int RELOAD_TIME_MILLIS = 10000;

        timerTaskReload = new TimerTask() {
            @Override
            public void run() {
                /* Check Fragment is attached to Activity to avoid NullPointerExceptions. */
                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (shouldAutoReload) {
                                loadStopForecast(
                                        Preferences.selectedStopName(
                                                getActivity().getApplicationContext(),
                                                line
                                        ),
                                        false
                                );
                            }
                        }
                    });
                }
            }
        };

        /* Schedule the auto-reload task to run. */
        new Timer().schedule(timerTaskReload, delayTimeMillis, RELOAD_TIME_MILLIS);
    }

    private void loadStopForecast(String stopName, final boolean shouldShowSnackbar) {
        final String API_URL = "https://api.thecosmicfrog.org/cgi-bin";
        final String API_ACTION = "times";
        final String API_VER = "3";

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        ApiMethods methods = restAdapter.create(ApiMethods.class);

        Callback<ApiTimes> callback = new Callback<ApiTimes>() {
            @Override
            public void success(ApiTimes apiTimes, Response response) {
                if (isAdded()) {
                    if (apiTimes != null) {
                        StopForecast stopForecast = StopForecastUtil.createStopForecast(apiTimes);

                        clearStopForecast();
                        updateStopForecast(stopForecast);

                        if (shouldShowSnackbar) {
                            String apiCreatedTime = getApiCreatedTime(apiTimes);

                            if (apiCreatedTime != null) {

                            }
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Timber.e("Failure during call to server.");

                /*
                 * If we get a message or a response from the server, there's likely an issue with
                 * the client request or the server's response itself.
                 */
                if (retrofitError.getMessage() != null) {
                    Timber.e("Message: %s", retrofitError.getMessage());
                }

                if (retrofitError.getResponse() != null) {
                    if (retrofitError.getResponse().getUrl() != null) {
                        Timber.e("Response: %s", retrofitError.getResponse().getUrl());
                    }

                    Timber.e("Status: %s", retrofitError.getResponse().getStatus());

                    if (retrofitError.getResponse().getHeaders() != null) {
                        Timber.e("Headers: %s", retrofitError.getResponse().getHeaders().toString());
                    }

                    if (retrofitError.getResponse().getBody() != null) {
                        Timber.e("Body: %s", retrofitError.getResponse().getBody().toString());
                    }

                    if (retrofitError.getResponse().getReason() != null) {
                        Timber.e("Reason: %s", retrofitError.getResponse().getReason());
                    }
                }
                if (retrofitError.getKind() != null) {
                    Timber.e("Kind: %s", retrofitError.getKind().toString());
                }
            }
        };

        methods.getStopForecast(
                API_ACTION,
                API_VER,
                mapStopNameId.get(stopName),
                callback
        );
    }

    private String getApiCreatedTime(ApiTimes apiTimes) {
        try {
            Date currentTime = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
            ).parse(apiTimes.getCreatedTime());

            DateFormat dateFormat =
                    new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            return dateFormat.format(currentTime);
        } catch (NullPointerException e) {
            Timber.e("Failed to find content view during Snackbar creation.");
        } catch (ParseException e) {
            Timber.e("Failed to parse created time from API.");

        }

        return null;
    }

    /**
     * Draw stop forecast to screen.
     *
     * @param stopForecast StopForecast object containing data for requested stop.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private void updateStopForecast(StopForecast stopForecast) {
        final String GAEILGE = "ga";
        final String DUE = "DUE";

        EnglishGaeilgeMap mapEnglishGaeilge = new EnglishGaeilgeMap();
        String min = " " + getString(R.string.min);
        String mins = " " + getString(R.string.mins);
        String minOrMins;

            /*
             * Pull in all trams from the StopForecast, but only display up to five
             * inbound and outbound trams.
             */
            if (stopForecast.getInboundTrams() != null) {
                if (stopForecast.getInboundTrams().size() == 0) {
                    inboundStopForecastCardView.setNoTramsForecast();
                } else {
                    String inboundTram;

                    for (int i = 0; i < stopForecast.getInboundTrams().size(); i++) {
                        String dueMinutes =
                                stopForecast.getInboundTrams().get(i).getDueMinutes();

                        if (i < 8) {
                            if (localeDefault.startsWith(GAEILGE)) {
                                inboundTram = mapEnglishGaeilge.get(
                                        stopForecast.getInboundTrams()
                                                .get(i)
                                                .getDestination()
                                );
                            } else {
                                inboundTram = stopForecast.getInboundTrams()
                                        .get(i)
                                        .getDestination();
                            }

                            if (dueMinutes.equalsIgnoreCase(DUE)) {
                                minOrMins = "";
                            } else if (Integer.parseInt(dueMinutes) > 1) {
                                minOrMins = mins;
                            } else {
                                minOrMins = min;
                            }

                            inboundStopForecastCardView.setStopNames(
                                    i,
                                    inboundTram
                            );

                            inboundStopForecastCardView.setStopTimes(
                                    i,
                                    dueMinutes + minOrMins
                            );
                        }
                    }
                }
            }

            if (stopForecast.getOutboundTrams() != null) {
                if (stopForecast.getOutboundTrams().size() == 0) {
                    outboundStopForecastCardView.setNoTramsForecast();
                } else {
                    String outboundTram;

                    for (int i = 0; i < stopForecast.getOutboundTrams().size(); i++) {
                        String dueMinutes =
                                stopForecast.getOutboundTrams().get(i).getDueMinutes();

                        if (i < 6) {
                            if (localeDefault.startsWith(GAEILGE)) {
                                outboundTram = mapEnglishGaeilge.get(
                                        stopForecast.getOutboundTrams()
                                                .get(i)
                                                .getDestination()
                                );
                            } else {
                                outboundTram =
                                        stopForecast.getOutboundTrams()
                                                .get(i).getDestination();
                            }

                            if (dueMinutes.equalsIgnoreCase(DUE)) {
                                if (localeDefault.startsWith(GAEILGE)) {
                                    dueMinutes = mapEnglishGaeilge.get(dueMinutes);
                                }

                                minOrMins = "";
                            } else if (Integer.parseInt(dueMinutes) > 1) {
                                minOrMins = mins;
                            } else {
                                minOrMins = min;
                            }

                            outboundStopForecastCardView.setStopNames(
                                    i,
                                    outboundTram
                            );

                            outboundStopForecastCardView.setStopTimes(
                                    i,
                                    dueMinutes + minOrMins
                            );
                        }
                    }
                }
            }
        }
}

