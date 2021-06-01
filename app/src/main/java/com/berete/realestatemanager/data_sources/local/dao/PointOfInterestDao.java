package com.berete.realestatemanager.data_sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.berete.realestatemanager.data_sources.local.entities.PointOfInterestEntity;

import java.util.List;

@Dao
public interface PointOfInterestDao {

  @Query(
      "SELECT * FROM point_of_interest "
          + "JOIN property_point_of_interest_association AS ppoi_assoc "
          + "ON ppoi_assoc.property_id = :property_id")
  List<PointOfInterestEntity> getPointOfInterestByPropertyId(int property_id);

  @Update
  void update(PointOfInterestEntity pointOfInterest);

  @Delete
  void delete(PointOfInterestEntity pointOfInterest);

  @Insert
  void create(PointOfInterestEntity pointOfInterest);
}
