package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.Property;

@Entity(tableName = "point_of_interest")
public class PointOfInterestEntity extends Property.PointOrInterest {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public PointOfInterestEntity(String name) {
    super(name);
  }
}
