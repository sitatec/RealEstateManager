package com.berete.realestatemanager.ui.map;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.berete.realestatemanager.databinding.MapWindowLayoutBinding;
import com.berete.realestatemanager.domain.models.Property;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

  MapWindowLayoutBinding binding;

  public CustomInfoWindow(LayoutInflater inflater){
    binding = MapWindowLayoutBinding.inflate(inflater);
  }

  @Nullable
  @Override
  public View getInfoWindow(@NonNull Marker marker) {
    final Property property = (Property) marker.getTag();
    binding.setProperty(property);

    Glide.with(binding.getRoot())
        .load(property.getMainPhotoUrl())
        .centerCrop()
        .into(binding.photo);

    return binding.getRoot();
  }

  @Nullable
  @Override
  public View getInfoContents(@NonNull Marker marker) {
    return null;
  }
}
