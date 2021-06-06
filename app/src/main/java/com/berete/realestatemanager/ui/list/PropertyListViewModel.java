package com.berete.realestatemanager.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.repositories.PropertyRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PropertyListViewModel extends ViewModel {

  private final PropertyRepository propertyRepository;
  private LiveData<List<Property>> allProperties;

  @Inject
  public PropertyListViewModel(PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }

  public LiveData<List<Property>> getProperties(){
    if(allProperties == null){
      allProperties = propertyRepository.getAll();
    }
    return allProperties;
  }

}
