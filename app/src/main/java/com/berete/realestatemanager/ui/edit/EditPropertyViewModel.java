package com.berete.realestatemanager.ui.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.berete.realestatemanager.domain.models.Photo;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.models.Property.PointOfInterest;
import com.berete.realestatemanager.domain.models.RealEstateAgent;
import com.berete.realestatemanager.domain.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditPropertyViewModel extends ViewModel {

  public static final RealEstateAgent AGENT_PLACEHOLDER =
      new RealEstateAgent("Select Agent", "file:///android_asset/person.png");
  private final PropertyRepository propertyRepository;
  // For the update mode
  private final MutableLiveData<PropertyDataBinding> propertyToBeUpdated = new MutableLiveData<>();
  private final Executor doInBackground = Executors.newSingleThreadExecutor();
  private Property newProperty; // For the creation mode

  private MutableLiveData<List<Photo>> photoList = new MutableLiveData<>();
  private MutableLiveData<List<PointOfInterest>> pointOfInterestList = new MutableLiveData<>();

  @Inject
  public EditPropertyViewModel(PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }

  public LiveData<PropertyDataBinding> updateProperty(int propertyId) {
    doInBackground.execute(
        () -> {
          final Property property = propertyRepository.getById(propertyId);
          propertyToBeUpdated.setValue(new PropertyDataBinding(property));
        });
    return propertyToBeUpdated;
  }

  public PropertyDataBinding createNewProperty() {
    newProperty = new Property();
    newProperty.setPhotoList(new ArrayList<>());
    newProperty.setAddress(new Property.Address());
    return new PropertyDataBinding(newProperty);
  }

  public void persist() {
    if (newProperty != null) {
      propertyRepository.create(newProperty);
    } else {
      propertyRepository.update(propertyToBeUpdated.getValue().getProperty());
    }
  }

  public List<RealEstateAgent> getAllAgents() {
    final List<RealEstateAgent> agentList = new ArrayList<>();
    agentList.add(0, AGENT_PLACEHOLDER);
    return agentList;
  }

  public LiveData<List<PointOfInterest>> getAllPointsOfInterests(){
    return pointOfInterestList;
  }

  public LiveData<List<PointOfInterest>> getCurrentPropertyPointOfInterests(){
    return pointOfInterestList;
  }

  public void addPointOfInterestToCurrentProperty(int pointOfInterestId){

  }

  public void removePointOrInterestFromCurrentProperty(int pointOfInterestId){

  }

  public LiveData<Integer> createPointOfInterest(PointOfInterest pointOfInterest){
    final MutableLiveData<Integer> pointOfInterestId = new MutableLiveData<>();
    return pointOfInterestId;
  }

  public boolean containsPointOfInterest(int pointOfInterestId){
    return false;
  }

  public void createPhotoAndAddToCurrentProperty(Photo photo){

  }

  public LiveData<List<Photo>> getPropertyPhotos(){
    return photoList;
  }

}
