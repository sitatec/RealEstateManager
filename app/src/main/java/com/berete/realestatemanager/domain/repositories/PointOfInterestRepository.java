package com.berete.realestatemanager.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Ignore;

import com.berete.realestatemanager.domain.data_providers.PointOfInterestProvider;
import com.berete.realestatemanager.domain.models.Property;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class PointOfInterestRepository {
  private PointOfInterestProvider pointOfInterestProvider;
  private final Executor doInBackground = Executors.newFixedThreadPool(2);

  @Inject
  public PointOfInterestRepository(PointOfInterestProvider pointOfInterestProvider) {
    this.pointOfInterestProvider = pointOfInterestProvider;
  }

  public LiveData<Integer> create(Property.PointOfInterest pointOfInterests){
    return pointOfInterestProvider.create(pointOfInterests);
  }

  public void update(Property.PointOfInterest pointOfInterest){
    doInBackground.execute(() -> pointOfInterestProvider.update(pointOfInterest));
  }

  public void delete(Property.PointOfInterest pointOfInterest){
    doInBackground.execute(() -> pointOfInterestProvider.delete(pointOfInterest));
  }

  public LiveData<List<Property.PointOfInterest>> getAll(){
    return pointOfInterestProvider.getAll();
  }

  public LiveData<List<Property.PointOfInterest>> getByProperty(int propertyId){
    return pointOfInterestProvider.getPointOfInterestByPropertyId(propertyId);
  }

}
