package com.berete.realestatemanager.data_sources.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.berete.realestatemanager.data_sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data_sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data_sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data_sources.local.entities.Relationships;
import com.berete.realestatemanager.data_sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data_sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data_sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data_sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;

@androidx.room.Database(
    entities = {
      PropertyEntity.class,
      PhotoEntity.class,
      RealEstateAgentEntity.class,
      PointOfInterestEntity.class,
      Relationships.PropertyPointOfInterestCrossRef.class
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
        instance =
            Room.databaseBuilder(context, Database.class, "real_estate_manager.db")
                .addCallback(prepopulate)
                .build();
      }
    }
    return instance;
  }

  public static Database getTestInstance(Context context) {
    return Room.inMemoryDatabaseBuilder(context, Database.class).build();
  }

  private static final Callback prepopulate = new Callback() {
    @Override
    public void onCreate(@NotNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      final RealEstateAgentDao agentDao = instance.getRealEstateAgentDao();
      Executors.newSingleThreadExecutor().execute(() -> {
        agentDao.create(new RealEstateAgentEntity("Moussa Barry", "file:///android_asset/agent.jpeg"));
        agentDao.create(new RealEstateAgentEntity("Ibrahim Sorry", "file:///android_asset/agent_1.jpeg"));
        agentDao.create(new RealEstateAgentEntity("Alex Mason", "file:///android_asset/agent_2.jpeg"));
        agentDao.create(new RealEstateAgentEntity("Alexa Mason", "file:///android_asset/agent_3.jpeg"));
      });
    }
  };

}
