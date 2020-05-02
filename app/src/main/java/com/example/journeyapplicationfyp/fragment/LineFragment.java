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
import com.example.journeyapplicationfyp.api.Api;
import com.example.journeyapplicationfyp.object.EnglishGaeilgeMap;
import com.example.journeyapplicationfyp.object.Luas;
import com.example.journeyapplicationfyp.object.Luas_Stop;
import com.example.journeyapplicationfyp.object.StopNameIdMap;
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
                Timber.tag(LOG_TAG).wtf("Invalid line specified.");
        }

        if (listStopsThisLine == null) {
            Timber.e("List of stops for this line is null.");

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
            return false;
        }
    }

    private void clearStopForecast() {
        inboundStopForecastCardView.clearStopForecast();
        outboundStopForecastCardView.clearStopForecast();
    }

    private void loadStopForecast(String stopName, final boolean shouldShowSnackbar) {
        final String API_URL = "https://api.thecosmicfrog.org/cgi-bin";
        final String API_ACTION = "times";
        final String API_VER = "3";

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        Api methods = restAdapter.create(Api.class);

        Callback<Luas> callback = new Callback<Luas>() {
            @Override
            public void success(Luas apiTimes, Response response) {
                if (isAdded()) {
                    if (apiTimes != null) {
                        Luas_Stop luasStop = StopForecastUtil.createStopForecast(apiTimes);

                        clearStopForecast();
                        updateStopForecast(luasStop);

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

    private String getApiCreatedTime(Luas luas) {
        try {
            Date currentTime = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
            ).parse(luas.getCreatedTime());

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


    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private void updateStopForecast(Luas_Stop luasStop) {
        final String GAEILGE = "ga";
        final String DUE = "DUE";

        EnglishGaeilgeMap mapEnglishGaeilge = new EnglishGaeilgeMap();
        String min = " " + getString(R.string.min);
        String mins = " " + getString(R.string.mins);
        String minOrMins;

        if (luasStop.getInboundTrams() != null) {
            if (luasStop.getInboundTrams().size() == 0) {
                    inboundStopForecastCardView.setNoTramsForecast();
                } else {
                    String inboundTram;

                for (int i = 0; i < luasStop.getInboundTrams().size(); i++) {
                        String dueMinutes =
                                luasStop.getInboundTrams().get(i).getDueMinutes();

                        if (i < 8) {
                            if (localeDefault.startsWith(GAEILGE)) {
                                inboundTram = mapEnglishGaeilge.get(
                                        luasStop.getInboundTrams()
                                                .get(i)
                                                .getDestination()
                                );
                            } else {
                                inboundTram = luasStop.getInboundTrams()
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

        if (luasStop.getOutboundTrams() != null) {
            if (luasStop.getOutboundTrams().size() == 0) {
                    outboundStopForecastCardView.setNoTramsForecast();
                } else {
                    String outboundTram;

                for (int i = 0; i < luasStop.getOutboundTrams().size(); i++) {
                        String dueMinutes =
                                luasStop.getOutboundTrams().get(i).getDueMinutes();

                        if (i < 6) {
                            if (localeDefault.startsWith(GAEILGE)) {
                                outboundTram = mapEnglishGaeilge.get(
                                        luasStop.getOutboundTrams()
                                                .get(i)
                                                .getDestination()
                                );
                            } else {
                                outboundTram =
                                        luasStop.getOutboundTrams()
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

