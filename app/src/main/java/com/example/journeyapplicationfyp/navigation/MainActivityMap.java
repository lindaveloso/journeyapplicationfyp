package com.example.journeyapplicationfyp.navigation;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.journeyapplicationfyp.R;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private PermissionsManager permissionsManager;
    private MapView mapView;
    private Point origin;
    private Point destination;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private SearchDialogFragment searchDialogFragment;
    private NavigationViewModel viewModel;
    private MapboxMap mapBox;

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
        Settings();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NavigationViewModel.class);
        viewModel.selected.observe(requireActivity(), navigationState -> {
            Toast.makeText(requireContext(), "" + mapBox, Toast.LENGTH_SHORT).show();
            if (mapBox != null) {
                Toast.makeText(requireContext(), "" + mapBox, Toast.LENGTH_SHORT).show();
                //getRoute(mapBox, navigationState.origin, navigationState.destination);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapBox = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
// Set the origin location to the Alhambra landmark in Granada, Spain.
                enableLocationComponent(style);
                origin = Point.fromLngLat(53.3938952, -6.3942532);

// Set the destination location to the Plaza del Triunfo in Granada, Spain
                destination = Point.fromLngLat(53.3973931, -6.4003374);

                initSource(style);

                initLayers(style);
                getRoute(mapboxMap, origin, destination);

                GeoJSONToMap("luas-points-greenline", "luas-points-greenline", "asset://luas_greenline.geojson");
                GeoJSONToMap2("luas-points-redline", "luas-points-redline", "asset://luas_redline.geojson");
                GeoJSONToMap3("demo-data-dubin-bus-points", "demo-data-dubin-bus-points", "asset://dublin_bus_points.geojson");

            }
        });
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



    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude()))}));
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
                lineColor(Color.parseColor("#009688"))
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
                Timber.d("Response code: " + response.code());
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
            mapBox.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
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
}
