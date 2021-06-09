package com.berete.realestatemanager.ui.edit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.berete.realestatemanager.domain.models.Photo;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.models.Property.PointOfInterest;
import com.berete.realestatemanager.domain.models.RealEstateAgent;
import com.berete.realestatemanager.domain.repositories.AgentRepository;
import com.berete.realestatemanager.domain.repositories.PhotoRepository;
import com.berete.realestatemanager.domain.repositories.PointOfInterestRepository;
import com.berete.realestatemanager.domain.repositories.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditPropertyViewModel extends ViewModel {
  private LiveData<Integer> livePointOfInterestId;

  // DATA_SOURCES
  private final PropertyRepository propertyRepository;
  private final PhotoRepository photoRepository;
  private final PointOfInterestRepository pointOfInterestRepository;
  private final AgentRepository agentRepository;

  private final MutableLiveData<PropertyDataBinding> propertyBindingLiveData =
      new MutableLiveData<>();
  private Property currentProperty;

  private LiveData<List<PointOfInterest>> allPointOfInterest;

  private LiveData<List<RealEstateAgent>> allAgents;

  @Inject
  public EditPropertyViewModel(
      PropertyRepository propertyRepository,
      PhotoRepository photoRepository,
      PointOfInterestRepository pointOfInterestRepository,
      AgentRepository agentRepository) {
    this.propertyRepository = propertyRepository;
    this.photoRepository = photoRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.agentRepository = agentRepository;
  }

  public LiveData<PropertyDataBinding> updateProperty(int propertyId) {

    final LiveData<Property> currentPropertyLiveData = propertyRepository.getById(propertyId);
    currentPropertyLiveData.observeForever(
        new Observer<Property>() {

          @Override
          public void onChanged(Property property) {
            currentProperty = property;
            final PropertyDataBinding propertyDataBinding = new PropertyDataBinding(currentProperty);
            propertyDataBinding.setSelectedAgentPosition(currentProperty.getAgent().getId());
            propertyBindingLiveData.setValue(propertyDataBinding);
            currentPropertyLiveData.removeObserver(this);
          }
        });

    return propertyBindingLiveData;
  }

  public LiveData<PropertyDataBinding> createNewProperty() {
    currentProperty = new Property();
    currentProperty.setPhotoList(new ArrayList<>());
    currentProperty.setAddress(new Property.Address());
    currentProperty.setPointOfInterestNearby(new ArrayList<>());
    propertyBindingLiveData.setValue(new PropertyDataBinding(currentProperty));
    return propertyBindingLiveData;
  }

  public void persist() {
    // TODO REFACTORING

    propertyBindingLiveData.getValue().apply();
    int selectedItemPos = propertyBindingLiveData.getValue().getSelectedAgentPosition();
    currentProperty.setAgent(allAgents.getValue().get(selectedItemPos));

    if (currentProperty.getMainPhotoUrl().isEmpty()) {
      currentProperty.setMainPhotoUrl(currentProperty.getPhotoList().get(0).getUrl());
    }

    LiveData<Integer> livePropertyId;
    if (currentProperty.getId() == 0) {
      livePropertyId = propertyRepository.create(currentProperty);
    } else {
      propertyRepository.update(currentProperty);
      livePropertyId = new MutableLiveData<>(currentProperty.getId());
    }

    livePropertyId.observeForever(
        new Observer<Integer>() {
          @Override
          public void onChanged(Integer propertyId) {

            for (PointOfInterest pointOfInterest : currentProperty.getPointOfInterestNearby()) {
              livePointOfInterestId = pointOfInterestRepository.create(pointOfInterest);
              livePointOfInterestId.observeForever(
                  new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer pointOfInterestId) {
                      propertyRepository.addPointOfInterestToProperty(
                          propertyId, pointOfInterestId);
                      livePointOfInterestId.removeObserver(this);
                    }
                  });
            }

            final Photo[] propertyPhotos =
                currentProperty.getPhotoList().stream()
                    .peek(photo -> photo.setPropertyId(propertyId))
                    .toArray(Photo[]::new);

            photoRepository.create(propertyPhotos);

            livePropertyId.removeObserver(this);
          }
        });
  }

  public LiveData<List<RealEstateAgent>> getAllAgents() {
    allAgents = agentRepository.getAll();
    return allAgents;
  }

  public LiveData<List<PointOfInterest>> getAllPointsOfInterests() {
    if (allPointOfInterest == null) {
      allPointOfInterest = pointOfInterestRepository.getAll();
    }
    return allPointOfInterest;
  }

  public void addPointOfInterestToCurrentProperty(PointOfInterest pointOfInterest) {
    currentProperty.getPointOfInterestNearby().add(pointOfInterest);
  }

  public void removePointOrInterestFromCurrentProperty(PointOfInterest pointOfInterest) {
    currentProperty.getPointOfInterestNearby().remove(pointOfInterest);
  }

  public LiveData<Integer> createPointOfInterest(PointOfInterest pointOfInterest) {
    return pointOfInterestRepository.create(pointOfInterest);
  }

  public boolean containsPointOfInterest(PointOfInterest pointOfInterest) {
    return currentProperty.getPointOfInterestNearby().contains(pointOfInterest);
  }

  public void addPhotoToCurrentProperty(Photo photo) {
    currentProperty.getPhotoList().add(photo);
  }

  public List<Photo> getPropertyPhotos() {
    return currentProperty.getPhotoList();
  }

  public void setMainPhoto(Photo photo) {
    currentProperty.setMainPhotoUrl(photo.getUrl());
  }

  public boolean isPhotoDefined() {
    return !currentProperty.getPhotoList().isEmpty();
  }
}
