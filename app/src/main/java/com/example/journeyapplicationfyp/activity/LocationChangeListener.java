package com.example.journeyapplicationfyp.activity;

import android.app.Activity;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.lang.ref.WeakReference;

public class LocationChangeListener implements LocationEngineCallback<LocationEngineResult> {

    private final WeakReference<Activity> activityWeakReference;

    private final MapboxMap mapbox;

    public LocationChangeListener(WeakReference<Activity> activityWeakReference, MapboxMap mapbox) {
        this.activityWeakReference = activityWeakReference;
        this.mapbox = mapbox;
    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        Activity activity = activityWeakReference.get();
        if (activity != null){
            Location location = result.getLastLocation();
            if (location == null) {
                return;
            }

            if ( result.getLastLocation() != null) {
                mapbox.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }
        }

    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        if (activityWeakReference.get() != null) {
            Toast.makeText(activityWeakReference.get(), exception.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
