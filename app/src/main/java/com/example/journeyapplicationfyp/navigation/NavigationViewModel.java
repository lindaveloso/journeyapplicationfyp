package com.example.journeyapplicationfyp.navigation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.geojson.Point;

public class NavigationViewModel extends ViewModel {

    private MutableLiveData<NavigationState> _selected = new MutableLiveData<>();
    public LiveData<NavigationState> selected = _selected;

    public void onNavigationSelected(Point origin, Point destination) {
        _selected.postValue(new NavigationState(origin, destination));
    }
}
