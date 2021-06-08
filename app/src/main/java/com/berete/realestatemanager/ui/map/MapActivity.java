package com.berete.realestatemanager.ui.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.detail.PropertyDetailActivity;
import com.berete.realestatemanager.ui.list.PropertyListViewModel;
import com.berete.realestatemanager.utils.LocationUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class MapActivity extends AppCompatActivity {

  LocationUtil locationUtil;
  GoogleMap map;
  PropertyListViewModel viewModel;
  @Inject public LocationProvider locationProvider;
  CameraUpdate currentLocationCamera;

  @Override
  protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    final SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this::setupMap);
    setupActionsButtons();
    viewModel = new ViewModelProvider(this).get(PropertyListViewModel.class);
    locationUtil = new LocationUtil(this);
  }

  @SuppressLint("MissingPermission")
  private void setupMap(GoogleMap map) {
    this.map = map;
    map.getUiSettings().setMapToolbarEnabled(false);
    map.setInfoWindowAdapter(new CustomInfoWindow(getLayoutInflater()));
    map.setOnInfoWindowClickListener(this::onInfoWindowClickListener);
    locationProvider.getCurrentCoordinates(
        location -> {
          map.setMyLocationEnabled(true);
          map.getUiSettings().setMyLocationButtonEnabled(false);
          currentLocationCamera =
              CameraUpdateFactory.newLatLngZoom(
                  new LatLng(location.getLatitude(), location.getLongitude()), 14);
          map.animateCamera(currentLocationCamera);
          viewModel.getProperties().observe(this, this::onPropertiesFetched);
        });
  }

  private void onInfoWindowClickListener(Marker marker) {
    final Bundle detailActivityArgs = new Bundle();
    final int selectedPropertyId = ((Property) marker.getTag()).getId();
    detailActivityArgs.putInt(PropertyDetailActivity.PROPERTY_ID_ARG_KEY, selectedPropertyId);
    final Intent detailActivityIntent = new Intent(this, PropertyDetailActivity.class);
    detailActivityIntent.putExtras(detailActivityArgs);
    startActivity(detailActivityIntent);
  }

  private void onPropertiesFetched(List<Property> properties) {
    map.clear();
    final LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
    LocationUtil.GeoCoordinates currentPropertyCoordinates;
    for (Property currentProperty : properties) {
      currentPropertyCoordinates =
          locationUtil.getCoordinatesFromAddress(
              currentProperty.getAddress().getFormattedAddress(), bounds);
      if (currentPropertyCoordinates != null) {
        showPropertyMarker(currentProperty, currentPropertyCoordinates);
      }
    }
  }

  private void showPropertyMarker(Property property, LocationUtil.GeoCoordinates coordinates) {
    map.addMarker(
            new MarkerOptions()
                .position(coordinates.toLatLng())
                .title(property.getType().name())
                .snippet(property.getAddress().getLocality())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_location)))
        .setTag(property);
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  private void setupActionsButtons() {
    findViewById(R.id.backButton).setOnClickListener(__ -> finish());
    findViewById(R.id.myLocationButton)
        .setOnClickListener(__ -> map.animateCamera(currentLocationCamera));
  }
}
