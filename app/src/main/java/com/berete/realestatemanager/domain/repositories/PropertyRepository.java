package com.berete.realestatemanager.domain.repositories;

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
  public PropertyRepository(PropertyProvider propertyProvider){
    this.propertyProvider = propertyProvider;
  }

  public void create(Property property) {
    doInBackground.execute(() -> propertyProvider.create(property));
  }

  public Property getById(int id){
    return propertyProvider.getById(id);
  }

  public List<Property> getAll(){
    return propertyProvider.getAll();
  }

  public void update(Property property) {
    doInBackground.execute(() -> propertyProvider.update(property));
  }

  public void delete(Property property) {
    doInBackground.execute(() -> propertyProvider.delete(property));
  }

}
