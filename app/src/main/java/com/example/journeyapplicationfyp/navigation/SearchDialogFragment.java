package com.example.journeyapplicationfyp.navigation;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchDialogFragment extends DialogFragment {

    private View dialogView;
    private Point origin;
    private Point destination;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NavigationViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.map_search_fragment, null);
        return new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NavigationViewModel.class);
        initViews(view);
    }

    private void initViews(View view) {
        Button canelBtn = view.findViewById(R.id.cancel_btn);
        Button okBtn = view.findViewById(R.id.ok_btn);
        canelBtn.setOnClickListener(v -> {
            dismiss();
        });

        okBtn.setOnClickListener(v -> {
            if (origin == null) {
                Toast.makeText(requireContext(), "Please select a starting point!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (destination == null) {
                Toast.makeText(requireContext(), "Please select a destination!", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.onNavigationSelected(origin, destination);
            dismiss();
        });

        AutoCompleteTextView destinationText = view.findViewById(R.id.search_destination);

        PlacesAdapter destinationAdapter = new PlacesAdapter(requireContext(), R.layout.place_item_view, new ArrayList<>());
        destinationText.setAdapter(destinationAdapter);

        AutoCompleteTextView originText = view.findViewById(R.id.search_origin);
        PlacesAdapter originAdapter = new PlacesAdapter(requireContext(), R.layout.place_item_view, new ArrayList<>());
        originText.setAdapter(originAdapter);

        ProgressBar progressBar = view.findViewById(R.id.progress_circular);

        setUpDropDown(originText, originAdapter, progressBar);
        setUpDropDown(destinationText, destinationAdapter, progressBar);

        originText.setOnItemClickListener((parent, view12, position, id) -> {
            Places selected = (Places) parent.getItemAtPosition(position);
            origin = selected.point;
        });

        destinationText.setOnItemClickListener((parent, view1, position, id) -> {
            Places selected = (Places) parent.getItemAtPosition(position);
            destination = selected.point;
        });
    }

    private void setUpDropDown(AutoCompleteTextView destination, PlacesAdapter placesAdapter, ProgressBar progressBar) {
        Disposable destinationObservable = SerchObservable.fromTextView(destination)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty() && text.length() >= 3)
                .map(text -> text.toLowerCase())
                .distinctUntilChanged()
                .flatMap((Function<String, ObservableSource<List<CarmenFeature>>>) s -> {
                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.VISIBLE);
                    });
                    MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                            .accessToken(requireContext().getString(R.string.access_token))
                            .query(s)
                            .build();
                    return SerchObservable.search(s, mapboxGeocoding);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(carmenFeatures -> {

                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        ArrayList<Places> items = getPlaces(carmenFeatures);
                        placesAdapter.clear();
                        placesAdapter.addAll(items);
                        placesAdapter.notifyDataSetChanged();
                    });


                }, e -> {
                    Log.e("Search fragment", "Error: " + e.getMessage());
                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                    });
                });
        compositeDisposable.add(destinationObservable);
    }

    private ArrayList<Places> getPlaces(List<CarmenFeature> carmenFeatures) {
        ArrayList<Places> places = new ArrayList<>();
        for (CarmenFeature c : carmenFeatures) {
            places.add(new Places(c.placeName(), c.address(), c.center()));
            Log.e(requireActivity().getPackageName(), "" + c.center() + c.placeName());

        }
        return places;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
