package com.berete.realestatemanager.data_sources.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.berete.realestatemanager.data_sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data_sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data_sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data_sources.local.entities.EntitiesRelations;
import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data_sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data_sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data_sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

@androidx.room.Database(
    entities = {
      PropertyEntity.class,
      PhotoEntity.class,
      RealEstateAgentEntity.class,
      PointOfInterestEntity.class,
      EntitiesRelations.PropertyPointOfInterestCrossRef.class
    },
    version = 1,
    exportSchema = false)
public abstract class Database extends RoomDatabase {

  private static Database instance;

  public abstract PropertyDao getPropertyDao();

  public abstract RealEstateAgentDao getRealEstateAgentDao();

  public abstract PhotoDao getPhotoDao();

  public abstract PointOfInterestDao getPointOfInterestDao();

  public abstract PropertyPointOfInterestCrossRefDao getPropertyPointOfInterestCrossRefDao();

  public static synchronized Database getInstance(Context context) {
    if (instance == null) {
      synchronized (Database.class) {
        instance = Room.databaseBuilder(context, Database.class, "real_estate_manager.db").build();
      }
    }
    return instance;
  }

  public static Database getTestInstance(Context context) {
    return Room.inMemoryDatabaseBuilder(context, Database.class).build();
  }
}
