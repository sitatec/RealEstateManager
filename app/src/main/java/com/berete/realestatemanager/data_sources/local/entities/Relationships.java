package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import com.berete.realestatemanager.domain.models.Property;

import java.util.List;
import java.util.stream.Collectors;

public interface Relationships {

  class RealEstateAgentWithProperties {

    @Embedded public RealEstateAgentEntity realEstateAgent;

    @Relation(parentColumn = "agent_id", entityColumn = "agent_id", entity = PropertyEntity.class)
    public List<PropertyWithPhotosAndPointOfInterest> tempProperties;

    public List<Property> toProperties() {
      return this.tempProperties.stream()
          .map(
              propertyWithPhotosAndPointOfInterest -> {
                final Property property = propertyWithPhotosAndPointOfInterest.toProperty();
                property.setAgent(realEstateAgent);
                return property;
              })
          .collect(Collectors.toList());
    }
  }

  class PropertyWithPhotosAndPointOfInterest {

    @Embedded public PropertyEntity property;

    @Relation(
        parentColumn = "property_id",
        entityColumn = "point_of_interest_id",
        associateBy = @Junction(PropertyPointOfInterestCrossRef.class))
    public List<PointOfInterestEntity> pointOfInterest;

    @Relation(parentColumn = "property_id", entityColumn = "property_id")
    public List<PhotoEntity> photos;

    public Property toProperty() {
      property.setPhotoList(photos);
      property.setPointOfInterestNearby(pointOfInterest);
      return property;
    }
  }

  @Entity(
      primaryKeys = {"property_id", "point_of_interest_id"},
      tableName = "property_point_of_interest_association")
  class PropertyPointOfInterestCrossRef {

    @ColumnInfo(name = "property_id")
    public int propertyId;

    @ColumnInfo(name = "point_of_interest_id")
    public int pointOfInterestId;
  }
}
