package com.berete.realestatemanager.domain.repositories;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.data_providers.PropertyProvider;
import com.berete.realestatemanager.domain.models.Property;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class PropertyRepository {

  private final PropertyProvider propertyProvider;
  private final Executor doInBackground = Executors.newFixedThreadPool(2);

  @Inject
  public PropertyRepository(PropertyProvider propertyProvider) {
    this.propertyProvider = propertyProvider;
  }

  public LiveData<Integer> create(Property property) {
    return propertyProvider.create(property);
  }

  public Property getById(int id) {
    return propertyProvider.getById(id);
  }

  public LiveData<List<Property>> getAll() {
    return propertyProvider.getAll();
  }

  public void update(Property property) {
    doInBackground.execute(() -> propertyProvider.update(property));
  }

  public void delete(Property property) {
    doInBackground.execute(() -> propertyProvider.delete(property));
  }

  public void addPointOfInterestToProperty(int propertyId, int pointOfInterestId) {
    doInBackground.execute(
        () -> propertyProvider.associateWithPointOfInterest(propertyId, pointOfInterestId));
  }

  public void removePointOfInterestFromProperty(int propertyId, int pointOfInterestId) {
    doInBackground.execute(
        () -> propertyProvider.removePointOfInterestFromProperty(propertyId, pointOfInterestId));
  }
}
