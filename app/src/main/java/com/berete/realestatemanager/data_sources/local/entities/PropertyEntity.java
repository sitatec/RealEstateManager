package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.berete.realestatemanager.domain.models.Photo;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import java.util.List;

@Entity(tableName = "property")
public class PropertyEntity extends Property {

  @PrimaryKey(autoGenerate = true)
  public int id;

  @ColumnInfo(name = "agent_id")
  private int agentID;

  @Embedded private Address address;

  public PropertyEntity(
      Type type,
      double price,
      double surface,
      int numberOfRooms,
      String description,
      List<Photo> photoList,
      Address address,
      List<PointOrInterest> pointOfInterestNearby,
      boolean isAvailable,
      long availableSince,
      long saleDate,
      RealEstateAgent agent) {
    super(
        type,
        price,
        surface,
        numberOfRooms,
        description,
        photoList,
        address,
        pointOfInterestNearby,
        isAvailable,
        availableSince,
        saleDate,
        agent);
  }


}
