package com.example.journeyapplicationfyp.navigation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.activity.LocationChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.os.Looper.getMainLooper;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class MainActivityMap extends Fragment implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private PermissionsManager permissionsManager;

    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    FloatingActionButton fab_location_search;
    private MapView mapView;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private CarmenFeature home;
    private LocationEngine locationEngine;
    private LocationChangeListener callback;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private SearchDialogFragment searchDialogFragment;
    private NavigationViewModel viewModel;
    private CarmenFeature work;
    private MapboxMap mapBox;
    private Point origin;
    private Point destination;
    private Point currentLocation;
    int count = 0;
    private MutableLiveData<LocationState> currentLocationObsrvable = new MutableLiveData<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        View rootView = inflater.inflate(R.layout.activity_main_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        searchDialogFragment = new SearchDialogFragment();
        searchDialogFragment.show(getChildFragmentManager(), SearchDialogFragment.class.getSimpleName());
        fab_location_search = rootView.findViewById(R.id.fab_location_search);
        Settings();
        return rootView;
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(requireContext());

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NavigationViewModel.class);
        viewModel.selected.observe(getViewLifecycleOwner(), navigationState -> {
            if (navigationState == null) return;
            if (mapBox != null) {
                moveToMyLocation(navigationState.origin);
                getRoute(mapBox, navigationState.origin, navigationState.destination);
            }
        });
        currentLocationObsrvable.observe(getViewLifecycleOwner(), locationState -> {
            initSource(locationState.style, locationState.location);
            initLayers(locationState.style);
            currentLocationObsrvable.removeObservers(getViewLifecycleOwner());
        });
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapBox = mapboxMap;
        mapBox.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                showDialog(marker.getTitle() + " Luas Stop");

                return false;
            }
        });
        ///////////////////////  ADD MARKERS   ///////////////////
        AddMarkersToMap("luas_greenline.geojson", mapBox);
        AddMarkersToMap("luas_redline.geojson", mapBox);


        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Set the origin location to the Alhambra landmark in Granada, Spain.
                enableLocationComponent(style);
                callback =
                        new LocationChangeListener(new WeakReference<>(requireActivity()), mapBox) {
                            @Override
                            public void onSuccess(LocationEngineResult result) {
                                super.onSuccess(result);
                                currentLocation = Point.fromLngLat(result.getLastLocation().getLongitude(), result.getLastLocation().getLatitude());
                                currentLocationObsrvable.postValue(new LocationState(currentLocation, style));
                            }

                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                super.onFailure(exception);
                            }
                        };
                initLocationEngine();


                // origin = Point.fromLngLat(53.3938952, -6.3942532);
                //  origin = Point.fromLngLat(53.3938952,-6.3942532);

                // Set the destination location to the Plaza del Triunfo in Granada, Spain
                //  destination = Point.fromLngLat(53.3973931, -6.4003374);
                // destination = Point.fromLngLat(53.3953398,-6.4418919);

                //initSource(style);
                //  initSource(style);
                //  initLayers(style);
                // getRoute(mapboxMap, origin, destination);
/*              GeoJSONToMap("luas-points-greenline", "luas-points-greenline", "asset://luas_greenline.geojson");
                GeoJSONToMap2("luas-points-redline", "luas-points-redline", "asset://luas_redline.geojson");
                GeoJSONToMap3("demo-data-dubin-bus-points", "demo-data-dubin-bus-points", "asset://dublin_bus_points.geojson");*/
                initSearchFab();
                addUserLocations();

                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        requireActivity().getResources(), R.drawable.red_pin_marker));

                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);
// Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);

                SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setIconIgnorePlacement(true);
                symbolManager.addClickListener(symbol -> {

                            Toast.makeText(getActivity(), "gfgddd" + symbol.getId(), Toast.LENGTH_LONG).show();

                        }
                );


            }
        });
    }

    private void moveToMyLocation(@NotNull Point point) {
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(point.latitude(), point.longitude())) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mapBox.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
    }

    private void AddMarkersToMap(String jsonFile, MapboxMap mapboxMap) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(readJSONFromAsset(jsonFile));

            JSONArray features = obj.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject jsonObject = features.getJSONObject(i).getJSONObject("geometry");
                JSONArray cord = jsonObject.getJSONArray("coordinates");
                double lat = cord.getDouble(0);
                double lon = cord.getDouble(1);

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(features.getJSONObject(i).getJSONObject("properties").getString("Name")));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setPositiveButton(
                "Check",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* SearchActivity_Train fragment2=new SearchActivity_Train();*/
                        /*FragmentManager fm=getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=fm.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,fragment2,"tag");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();*/
                    }
                }

        );

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void GeoJSONToMap(String sourceId, String layerId, String asset_id) {
        mapBox.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                try {
                    GeoJsonSource source = new GeoJsonSource(sourceId, new URI("asset://luas_redline.geojson"));
                    style.addSource(source);
                    Bitmap icon;
                    if (layerId.equals("luas-points-redline")) {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.red_pin_marker);
                    } else {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.red_pin_marker);
                    }
                    style.addImage(layerId + " marker", icon);
                    SymbolLayer symbolLayer = new SymbolLayer(layerId, sourceId);

                    symbolLayer.setProperties(
                            iconImage(layerId + " marker"),
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true)
                    );

                    style.addLayer(symbolLayer);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void GeoJSONToMap2(String sourceId, String layerId, String asset_id) {
        mapBox.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                try {
                    GeoJsonSource source3 = new GeoJsonSource(sourceId, new URI("asset://luas_greenline.geojson"));
                    style.addSource(source3);
                    Bitmap icon;
                    if (layerId.equals("luas-points-greenline")) {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.green_pin_marker);
                    } else {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.green_pin_marker);
                    }
                    style.addImage(layerId + " marker", icon);
                    SymbolLayer symbolLayer = new SymbolLayer(layerId, sourceId);

                    symbolLayer.setProperties(
                            iconImage(layerId + " marker"),
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true)
                    );

                    style.addLayer(symbolLayer);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void GeoJSONToMap3(String sourceId, String layerId, String asset_id) {
        mapBox.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                try {
                    GeoJsonSource source4 = new GeoJsonSource(sourceId, new URI("asset://dublin_bus_points.geojson"));
                    style.addSource(source4);
                    Bitmap icon;
                    if (layerId.equals("demo-data-dubin-bus-points")) {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.bus_marker_pin);
                    } else {
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.bus_marker_pin);
                    }
                    style.addImage(layerId + " marker", icon);
                    SymbolLayer symbolLayer = new SymbolLayer(layerId, sourceId);

                    symbolLayer.setProperties(
                            iconImage(layerId + " marker"),
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true)
                    );

                    style.addLayer(symbolLayer);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void initSearchFab() {
        fab_location_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .addInjectedFeature(home)
                                .addInjectedFeature(work)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(requireActivity());
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void addUserLocations() {
        home = CarmenFeature.builder().text("Mapbox SF Office")
                .geometry(Point.fromLngLat(-122.3964485, 37.7912561))
                .placeName("50 Beale St, San Francisco, CA")
                .id("mapbox-sf")
                .properties(new JsonObject())
                .build();

        work = CarmenFeature.builder().text("Mapbox DC Office")
                .placeName("740 15th Street NW, Washington DC")
                .geometry(Point.fromLngLat(-77.0338348, 38.899750))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    //NEW LAYER
    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

// Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

// Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapBox != null) {
                Style style = mapBox.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }
                    // Move map camera to the selected location
                    mapBox.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);

                }
            }
        }
    }


    private void initSource(@NonNull Style loadedMapStyle, Point origin) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }


    /**
     * Add the route and marker icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);
// Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#3F51B5"))
        );
        loadedMapStyle.addLayer(routeLayer);

// Add the red marker icon image to the map
        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.map_marker_dark)));

// Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[]{0f, -9f})));
    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(getString(R.string.access_token))
                .build();


        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: %s", response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                // Get the directions route
                currentRoute = response.body().routes().get(0);


                // Make a toast which displays the route's distance
                Toast.makeText(getActivity(), "Response: " + currentRoute.distance(), Toast.LENGTH_SHORT).show();
// Draw the route on the map
                //  drawRoute(currentRoute);

                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

                            // Create a LineString with the directions route's geometry and
                            // reset the GeoJSON source for the route LineLayer source
                            if (source != null) {
                                source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(requireContext())) {
            LocationComponent locationComponent = mapBox.getLocationComponent();

            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle).build());

            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(requireActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(requireContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapBox.setStyle(Style.LIGHT,
                    new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            enableLocationComponent(style);
                        }
                    });
        } else {
            Toast.makeText(requireContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            requireActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        locationEngine.removeLocationUpdates(callback);
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void Settings() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            getActivity(),
                            R.color.system
                    )
            );

        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    //json
    private String readJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    class LocationState {
        public Point location;
        public Style style;

        public LocationState(Point location, Style style) {
            this.location = location;
            this.style = style;
        }
    }

}
