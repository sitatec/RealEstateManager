package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.Property;

@Entity(
    tableName = "property",
    ignoredColumns = {"photoList", "pointOfInterestNearby", "agent"})
public class PropertyEntity extends Property {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "property_id")
  public int id;

  @ColumnInfo(name = "agent_id")
  public int agentID;

  @Embedded public AddressEntity address;

  public PropertyEntity(
      Type type,
      double price,
      double surface,
      int numberOfRooms,
      String description,
      AddressEntity address,
      boolean isSold,
      long publicationDate,
      long saleDate) {
    super(
        type,
        price,
        surface,
        numberOfRooms,
        description,
        null,
        address,
        null,
        isSold,
        publicationDate,
        saleDate,
        null);
  }

  public PropertyEntity(Property parent) {
    super(
        parent.getType(),
        parent.getPrice(),
        parent.getSurface(),
        parent.getNumberOfRooms(),
        parent.getDescription(),
        parent.getPhotoList(),
        parent.getAddress(),
        parent.getPointOfInterestNearby(),
        parent.isSold(),
        parent.getPublicationDate(),
        parent.getSaleDate(),
        parent.getAgent());
    id = parent.getId();
  }

  public static class AddressEntity extends Address {

    @Ignore
    public AddressEntity(){}

    public AddressEntity(String locality, String postalCode, String formattedAddress){
      super(locality, postalCode, formattedAddress);
    }
  }
}
