package com.berete.realestatemanager.domain.data_providers;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.models.Property;

import java.util.List;

public interface PointOfInterestProvider {

  LiveData<List<Property.PointOfInterest>> getPointOfInterestByPropertyId(int property_id);

  LiveData<Integer> create(Property.PointOfInterest pointOfInterest);

  LiveData<List<Property.PointOfInterest>> getAll();

  void delete(Property.PointOfInterest pointOfInterest);

  void update(Property.PointOfInterest pointOfInterest);
}
