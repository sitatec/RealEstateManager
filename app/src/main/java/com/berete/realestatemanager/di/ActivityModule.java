package com.berete.realestatemanager.di;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

  @ActivityScoped
  @Provides
  public FusedLocationProviderClient fusedLocationProviderClient(@ApplicationContext Context context){
    return LocationServices.getFusedLocationProviderClient(context);
  }

}
