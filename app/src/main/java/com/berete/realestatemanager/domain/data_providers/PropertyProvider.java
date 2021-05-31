package com.berete.realestatemanager.domain.data_providers;

import com.berete.realestatemanager.domain.models.Property;

import java.util.List;

public interface PropertyProvider {
  Property getById(int id);
  List<Property> getAll();
  void update(Property property);
  void delete(Property property);
  void create(Property property);
}
