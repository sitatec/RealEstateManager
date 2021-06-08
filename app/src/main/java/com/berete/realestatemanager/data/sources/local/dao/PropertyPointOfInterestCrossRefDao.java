package com.berete.realestatemanager.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.berete.realestatemanager.data.sources.local.entities.Relationships;

@Dao
public interface PropertyPointOfInterestCrossRefDao {
  @Delete
  void delete(Relationships.PropertyPointOfInterestCrossRef associationClass);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void create(Relationships.PropertyPointOfInterestCrossRef associationClass);
}
