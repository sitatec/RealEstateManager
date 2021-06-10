package com.berete.realestatemanager.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.berete.realestatemanager.BuildConfig;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class LocationUtil {
  private static final String STATIC_MAP_BASE_URL =
      "https://maps.googleapis.com/maps/api/staticmap?key=" + BuildConfig.GOOGLE_MAP_STATIC_API_KEY;
  private final Geocoder geocoder;

  @Inject
  public LocationUtil(@ActivityContext Context context) {
    this.geocoder = new Geocoder(context, Locale.getDefault());
  }

  public GeoCoordinates getCoordinatesFromAddress(String address, LatLngBounds bounds) {
    List<Address> result = null;
    try {
      result =
          geocoder.getFromLocationName(
              address,
              1,
              bounds.southwest.latitude,
              bounds.southwest.longitude,
              bounds.northeast.latitude,
              bounds.northeast.longitude);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (result == null || result.isEmpty()) return null;
    return new GeoCoordinates(result.get(0).getLatitude(), result.get(0).getLongitude());
  }

  public GeoCoordinates getCoordinatesFromAddress(String address) {
    List<Address> result = null;
    try {
      result = geocoder.getFromLocationName(address, 1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (result == null || result.isEmpty()) return null;
    return new GeoCoordinates(result.get(0).getLatitude(), result.get(0).getLongitude());
  }

  public String getLocationStaticMapFromAddress(String address) {
    return STATIC_MAP_BASE_URL + "&size=200x200&zoom=15&markers=color:red|" + address;
  }

  // --------- INNERS ---------- //

  public static class GeoCoordinates {
    private final double latitude;
    private final double longitude;

    public GeoCoordinates(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public double getLatitude() {
      return latitude;
    }

    public double getLongitude() {
      return longitude;
    }

    public LatLng toLatLng() {
      return new LatLng(latitude, longitude);
    }
  }
}
