package com.berete.realestatemanager.data_sources.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;

@Dao
public interface PhotoDao {

  @Update
  void update(PhotoEntity property);

  @Delete
  void delete(PhotoEntity property);

  @Insert
  void create(PhotoEntity property);

}
