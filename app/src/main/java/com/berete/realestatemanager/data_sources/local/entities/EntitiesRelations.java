package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;


import java.util.List;

public interface EntitiesRelations {

  class RealEstateAgentWithProperties {

    @Embedded public RealEstateAgentEntity realEstateAgent;

    @Relation(parentColumn = "id", entityColumn = "agent_id", entity = PropertyEntity.class)
    public List<PropertyWithPhotosAndPointOfInterest> properties;
  }

  class PropertyWithPhotosAndPointOfInterest {

    @Embedded
    public PropertyEntity property;

    @Relation(
        parentColumn = "id",
        entityColumn = "point_of_interest_id",
        associateBy = @Junction(PropertyPointOfInterestCrossRef.class))
    public List<PointOfInterestEntity> pointOfInterest;

    @Relation(parentColumn = "id", entityColumn = "property_id")
    public List<PhotoEntity> photos;

  }

  @Entity(primaryKeys = {"id", "point_of_interest_id"})
  class PropertyPointOfInterestCrossRef {

    @ColumnInfo(name = "id")
    public int propertyId;

    @ColumnInfo(name = "point_of_interest_id")
    public int pointOfInterestId;
  }

}
