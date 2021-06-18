package com.berete.realestatemanager.ui.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.function.Consumer;

import javax.inject.Inject;

// TODO Refactoring
public class LocationProvider {

  private final FusedLocationProviderClient fusedLocationProviderClient;
  private final LocationPermissionHandler locationPermissionHandler;
  private Location lastKnownLocation;
  private Consumer<Location> onResultListener;


  @Inject
  public LocationProvider(
      FusedLocationProviderClient fusedLocationProviderClient, LocationPermissionHandler locationPermissionHandler) {
    this.locationPermissionHandler = locationPermissionHandler;
    this.fusedLocationProviderClient = fusedLocationProviderClient;
    warmUpTheLocationProvider();
  }

  @SuppressLint("MissingPermission")
  public void getCurrentCoordinates(Consumer<Location> listener) {
    if (locationPermissionHandler.hasPermission()) {
      fusedLocationProviderClient
          .getLastLocation()
          .addOnCompleteListener(getOnCompleteListener(listener));
    }else{
      onResultListener = listener;
      // The listener will be called int the warmUpTheLocationProvider.
    }
  }

  private OnCompleteListener<Location> getOnCompleteListener(Consumer<Location> listener) {
    return task -> {
      final Location location = task.getResult();
      if (task.isSuccessful() && location != null) {
        listener.accept(location);
        lastKnownLocation = location;
      } else if (lastKnownLocation != null) {
        listener.accept(lastKnownLocation);
      } else onResultListener = listener;
    };
  }

  /**
   * This method make sure that the a location has been requested at least once before requesting
   * the last know location, otherwise the last know location could always return null until a
   * client connect and request the current location update.
   */
  @SuppressLint("MissingPermission")
  private void warmUpTheLocationProvider() {
    final LocationRequest locationRequest =
        LocationRequest.create()
            .setInterval(100)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    if (locationPermissionHandler.hasPermission()) {
      fusedLocationProviderClient.requestLocationUpdates(
          locationRequest,
          new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
              super.onLocationResult(locationResult);
              lastKnownLocation = locationResult.getLastLocation();
              if (onResultListener != null) {
                onResultListener.accept(lastKnownLocation);
              }
              fusedLocationProviderClient.removeLocationUpdates(this);
            }
          },
          Looper.getMainLooper());
    } else locationPermissionHandler.requestPermission(this::warmUpTheLocationProvider);
  }

}
