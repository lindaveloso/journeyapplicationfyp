package com.example.journeyapplicationfyp.navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerchObservable {

    public static Observable<String> fromTextView(EditText view) {
        PublishSubject<String> publishSubject = PublishSubject.create();
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                publishSubject.onNext(s.toString());
            }
        });
        return publishSubject;
    }

    public static Observable<List<CarmenFeature>> search(String s, MapboxGeocoding mapboxGeocoding) {

        return Observable.create(emitter -> mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                List<CarmenFeature> results = response.body().features();
                emitter.onNext(results);

                if (results.size() > 0) {

                    // Log the first results Point.
                    Point firstResultPoint = results.get(0).center();
                    //Log.d(TAG, "onResponse: " + firstResultPoint.toString());

                } else {

                    // No result for your request were found.
                    //Log.d(TAG, "onResponse: No result found");

                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                emitter.onError(throwable);
            }
        }));

    }
}
