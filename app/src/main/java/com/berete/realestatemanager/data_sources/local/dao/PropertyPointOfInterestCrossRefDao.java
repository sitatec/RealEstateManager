package com.berete.realestatemanager.data_sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.berete.realestatemanager.data_sources.local.entities.EntitiesRelations;
import com.berete.realestatemanager.data_sources.local.entities.PointOfInterestEntity;

@Dao
public interface PropertyPointOfInterestCrossRefDao {
  @Delete
  void delete(EntitiesRelations.PropertyPointOfInterestCrossRef associationClass);

  @Insert
  void create(EntitiesRelations.PropertyPointOfInterestCrossRef associationClass);
}
