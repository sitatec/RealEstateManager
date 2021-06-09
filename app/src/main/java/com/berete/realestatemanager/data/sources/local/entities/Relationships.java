package com.berete.realestatemanager.data.sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Junction;
import androidx.room.Relation;

import com.berete.realestatemanager.domain.models.Photo;
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
    public List<PointOfInterestEntity> pointOfInterests;

    @Relation(parentColumn = "property_id", entityColumn = "property_id")
    public List<PhotoEntity> photos;

    public Property toProperty() {
      final List<Photo> photosModels =
          photos.stream().map(PhotoEntity::toModel).collect(Collectors.toList());

      final List<Property.PointOfInterest> pointOfInterestsModels =
          pointOfInterests.stream()
              .map(PointOfInterestEntity::toModel)
              .collect(Collectors.toList());

      property.setPhotoList(photosModels);
      property.setPointOfInterestNearby(pointOfInterestsModels);
      return property;
    }
  }

  @Entity(
      primaryKeys = {"property_id", "point_of_interest_id"},
      tableName = "property_point_of_interest_association",
      foreignKeys = {
        @ForeignKey(
            entity = PropertyEntity.class,
            parentColumns = "property_id",
            childColumns = "property_id",
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(
            entity = PointOfInterestEntity.class,
            parentColumns = "point_of_interest_id",
            childColumns = "point_of_interest_id",
            onDelete = ForeignKey.CASCADE)
      },
      indices = {
        @Index(name = "assoc_property_id_index", value = "property_id"),
        @Index(name = "assoc_point_of_interest_id_index", value = "point_of_interest_id")
      })
  class PropertyPointOfInterestCrossRef {

    @ColumnInfo(name = "property_id")
    public int propertyId;

    @ColumnInfo(name = "point_of_interest_id")
    public int pointOfInterestId;
  }
}
