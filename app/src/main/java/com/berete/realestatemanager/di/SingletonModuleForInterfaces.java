package com.berete.realestatemanager.di;

import com.berete.realestatemanager.data_sources.local.PropertyDataProvider;
import com.berete.realestatemanager.domain.data_providers.PropertyProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class SingletonModuleForInterfaces {

  @Binds
  @Singleton
  public abstract PropertyProvider bindPropertyProvider(PropertyDataProvider propertyDataProvider);

}
