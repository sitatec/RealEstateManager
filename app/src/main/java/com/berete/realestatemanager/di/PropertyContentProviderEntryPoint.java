package com.berete.realestatemanager.di;


import com.berete.realestatemanager.data.sources.local.Database;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface PropertyContentProviderEntryPoint {

  Database getDatabase();

}
