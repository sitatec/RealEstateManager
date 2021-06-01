package com.berete.realestatemanager.data_sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data_sources.local.entities.PointOfInterestEntity;

@Dao
public interface PointOfInterestDao {

  @Update
  void update(PointOfInterestEntity pointOfInterest);

  @Delete
  void delete(PointOfInterestEntity pointOfInterest);

  @Insert
  void create(PointOfInterestEntity pointOfInterest);

}
