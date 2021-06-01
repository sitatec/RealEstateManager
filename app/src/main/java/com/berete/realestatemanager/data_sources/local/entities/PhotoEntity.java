package com.berete.realestatemanager.data_sources.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.Photo;

@Entity(tableName = "photo_entity")
public class PhotoEntity extends Photo {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "photo_id")
  public int id;

  @ColumnInfo(name = "property_id")
  public int propertyId;

  public PhotoEntity(String url, String description) {
    super(url, description);
  }

  public Photo toModel(){
    return this;
  }
}
