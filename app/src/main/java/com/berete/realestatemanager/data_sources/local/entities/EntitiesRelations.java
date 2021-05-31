package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import com.berete.realestatemanager.domain.models.Photo;

import java.util.List;

public interface EntitiesRelations {

  class RealEstateAgentWithPhotoAndProperties {

    @Embedded private RealEstateAgentEntity realEstateAgent;

    @Relation(parentColumn = "id", entityColumn = "owner_id")
    private PhotoEntity photo;

    @Relation(parentColumn = "id", entityColumn = "agent_id", entity = PropertyEntity.class)
    private List<PropertyWithPhotosAndPointOfInterest> properties;
  }


  class PropertyWithPhotosAndPointOfInterest {

    @Embedded
    public PropertyEntity property;

    @Relation(parentColumn = "id", entityColumn = "owner_id")
    public List<Photo> photos;

    @Relation(
        parentColumn = "property_id",
        entityColumn = "point_of_interest_id",
        associateBy = @Junction(PropertyPointOfInterestCrossRef.class))
    public List<PointOfInterestEntity> pointOfInterest;
  }


  @Entity(primaryKeys = {"property_id", "point_of_interest_id"})
  class PropertyPointOfInterestCrossRef {

    @ColumnInfo(name = "property_id")
    public int propertyId;

    @ColumnInfo(name = "point_of_interest_id")
    public int pointOfInterestId;
  }

}
