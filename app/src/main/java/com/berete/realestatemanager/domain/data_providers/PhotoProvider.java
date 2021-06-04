package com.berete.realestatemanager.domain.data_providers;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.domain.models.Photo;

import java.util.List;

public interface PhotoProvider {
  LiveData<List<Photo>> getByPropertyId(int propertyId);

  void update(Photo photo);

  void delete(Photo photo);

  void create(Photo... photo);
}
