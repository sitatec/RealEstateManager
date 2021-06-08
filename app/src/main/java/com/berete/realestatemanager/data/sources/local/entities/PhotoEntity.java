package com.berete.realestatemanager.data.sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.Photo;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
    tableName = "photo_entity",
    foreignKeys =
        @ForeignKey(
            entity = PropertyEntity.class,
            parentColumns = "property_id",
            childColumns = "property_id",
            onDelete = CASCADE))
public class PhotoEntity extends Photo {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "photo_id")
  public int id;

  @ColumnInfo(name = "property_id")
  public int propertyId;

  public PhotoEntity(String url, String description) {
    super(url, description);
  }


  @Ignore
  public PhotoEntity(String url, String description, int propertyId) {
    super(url, description);
    this.propertyId = propertyId;
  }

  public PhotoEntity(Photo parent) {
    super(parent.getUrl(), parent.getDescription());
    if(parent.getId() != 0){
      id = parent.getId();
    }
    propertyId = parent.getPropertyId();
  }

  public Photo toModel() {
    return this;
  }
}
