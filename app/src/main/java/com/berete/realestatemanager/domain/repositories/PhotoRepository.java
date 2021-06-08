package com.berete.realestatemanager.domain.repositories;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.data_providers.PhotoProvider;
import com.berete.realestatemanager.domain.models.Photo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class PhotoRepository {
  private final PhotoProvider photoProvider;
  private final Executor doInBackground = Executors.newSingleThreadExecutor();

  @Inject
  public PhotoRepository(PhotoProvider photoProvider){
    this.photoProvider = photoProvider;
  }

  public LiveData<List<Photo>> getByPropertyId(int propertyId){
    return photoProvider.getByPropertyId(propertyId);
  }

  public void create(Photo... photo){
    doInBackground.execute(() -> photoProvider.create(photo));
  }

}
