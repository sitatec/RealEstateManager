package com.berete.realestatemanager.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.repositories.PointOfInterestRepository;
import com.berete.realestatemanager.domain.repositories.PropertyRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PropertyListViewModel extends ViewModel {

  private final PropertyRepository propertyRepository;
  private final PointOfInterestRepository pointOfInterestRepository;
  private LiveData<List<Property>> allProperties;
  private LiveData<List<Property.PointOfInterest>> allPointOfInterest;

  @Inject
  public PropertyListViewModel(PropertyRepository propertyRepository, PointOfInterestRepository pointOfInterestRepository) {
    this.propertyRepository = propertyRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
  }

  public LiveData<List<Property>> getProperties(){
    if(allProperties == null){
      allProperties = propertyRepository.getAll();
    }
    return allProperties;
  }

  public LiveData<List<Property.PointOfInterest>> getAllPointOfInterest(){
    if(allPointOfInterest == null){
      allPointOfInterest = pointOfInterestRepository.getAll();
    }
    return allPointOfInterest;
  }

}
