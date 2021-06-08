package com.berete.realestatemanager.ui.map;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.utils.CurrencyUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

  TextView propertyType;
  TextView propertyPrice;
  ImageView propertyMainPhoto;
  View viewRoot;
  ImageView soldBadge;
  int lasShowPropertyId;

  public CustomInfoWindow(LayoutInflater inflater) {
    viewRoot = inflater.inflate(R.layout.map_window_layout, null);
    propertyType = viewRoot.findViewById(R.id.propertyType);
    propertyPrice = viewRoot.findViewById(R.id.propertyPrice);
    propertyMainPhoto = viewRoot.findViewById(R.id.propertyPhoto);
    soldBadge = viewRoot.findViewById(R.id.soldBadge);
  }

  @Nullable
  @Override
  public View getInfoWindow(@NonNull Marker marker) {
    return null;
  }

  @Nullable
  @Override
  public View getInfoContents(@NonNull Marker marker) {
    final Property property = (Property) marker.getTag();
    if (lasShowPropertyId == property.getId()) return viewRoot;

    Glide.with(viewRoot).load(property.getMainPhotoUrl()).into(new CustomTarget<Drawable>() {
      @Override
      public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
        propertyMainPhoto.setImageDrawable(resource);
        propertyMainPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lasShowPropertyId = property.getId();
        marker.showInfoWindow();
      }

      @Override
      public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

      }
    });

    propertyPrice.setText(CurrencyUtils.convertDoubleToCurrency(property.getPrice()));
    propertyType.setText(property.getType().name());
    soldBadge.setVisibility(property.isSold() ? View.VISIBLE : View.INVISIBLE);

    return viewRoot;
  }
}
