package com.berete.realestatemanager.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

  private final Geocoder geocoder;

  public LocationUtil(Context context) {
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

    public LatLng toLatLng(){
      return new LatLng(latitude, longitude);
    }
  }
}
