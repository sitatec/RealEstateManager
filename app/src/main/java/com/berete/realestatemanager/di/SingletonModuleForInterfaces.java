package com.berete.realestatemanager.di;

import com.berete.realestatemanager.data.sources.local.adapters.PhotoDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.PointOfInterestDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.PropertyDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.RealEstateAgentDataProvider;
import com.berete.realestatemanager.domain.data_providers.PhotoProvider;
import com.berete.realestatemanager.domain.data_providers.PointOfInterestProvider;
import com.berete.realestatemanager.domain.data_providers.PropertyProvider;
import com.berete.realestatemanager.domain.data_providers.RealEstateAgentProvider;

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

  @Binds
  @Singleton
  public abstract PhotoProvider bindPropertyPhotoProvider(PhotoDataProvider photoDataProvider);

  @Binds
  @Singleton
  public abstract RealEstateAgentProvider bindAgentProvider(
      RealEstateAgentDataProvider realEstateAgentDataProvider);

  @Binds
  @Singleton
  public abstract PointOfInterestProvider bingPointOfInterestDataProvider(
      PointOfInterestDataProvider pointOfInterestDataProvider);
}
