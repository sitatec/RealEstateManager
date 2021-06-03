package com.berete.realestatemanager.di;

import android.content.Context;

import com.berete.realestatemanager.data_sources.local.Database;
import com.berete.realestatemanager.data_sources.local.PropertyDataProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SingletonModule {

  @Provides
  @Singleton
  public Database provideDatabase(@ApplicationContext Context applicationContext) {
    return Database.getInstance(applicationContext);
  }

  @Provides
  @Singleton
  public PropertyDataProvider providePropertyDataProvider(@NotNull Database database) {
    return new PropertyDataProvider(
        database.getRealEstateAgentDao(),
        database.getPropertyDao(),
        database.getPhotoDao(),
        database.getPointOfInterestDao());
  }
}
