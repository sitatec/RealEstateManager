package com.berete.realestatemanager.data_sources.local;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.berete.realestatemanager.data_sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data_sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data_sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

@androidx.room.Database(
    entities = {PropertyEntity.class, PhotoEntity.class, RealEstateAgent.class},
    version = 1)
public abstract class Database extends RoomDatabase {

  private static Database instance;

  public abstract PropertyDao getPropertyDao();
  public abstract RealEstateAgentDao getRealEstateAgentDao();
  public abstract PhotoDao getPhotoDao();

  public static synchronized Database getInstance(Application application) {
    if (instance == null) {
      synchronized (Database.class) {
        instance =
            Room.databaseBuilder(application, Database.class, "real_estate_manager.db").build();
      }
    }
    return instance;
  }
}
