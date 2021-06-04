package com.berete.realestatemanager.data_sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.berete.realestatemanager.data_sources.local.entities.Relationships;

@Dao
public interface PropertyPointOfInterestCrossRefDao {
  @Delete
  void delete(Relationships.PropertyPointOfInterestCrossRef associationClass);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void create(Relationships.PropertyPointOfInterestCrossRef associationClass);
}
