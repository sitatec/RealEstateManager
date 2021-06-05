package com.berete.realestatemanager.domain.data_providers;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.models.Property;

import java.util.List;

public interface PropertyProvider {
  LiveData<Property> getById(int id);
  LiveData<List<Property>> getAll();
  void update(Property property);
  void delete(Property property);
  LiveData<Integer> create(Property property);
  void associateWithPointOfInterest(int propertyId, int pointOfInterestId);
  void removePointOfInterestFromProperty(int propertyId, int pointOfInterestId);
}
