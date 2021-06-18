package com.berete.realestatemanager.data.sources.local.adapters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.berete.realestatemanager.data.sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data.sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data.sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data.sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data.sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data.sources.local.entities.Relationships;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.RealEstateAgentWithProperties;
import com.berete.realestatemanager.domain.data_providers.PropertyProvider;
import com.berete.realestatemanager.domain.models.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PropertyDataProvider implements PropertyProvider {

  private final RealEstateAgentDao realEstateAgentDao;
  private final PropertyDao propertyDao;
  private final PhotoDao photoDao;
  private final PointOfInterestDao pointOfInterestDao;
  private final PropertyPointOfInterestCrossRefDao propertyPointOfInterestCrossRefDao;

  private final Executor doInBackground = Executors.newSingleThreadExecutor();
  private MutableLiveData<List<Property>> allProperties;
  PropertyEntity propertyEntity;

  public PropertyDataProvider(
      RealEstateAgentDao realEstateAgentDao,
      PropertyDao propertyDao,
      PhotoDao photoDao,
      PointOfInterestDao pointOfInterestDao,
      PropertyPointOfInterestCrossRefDao propertyPointOfInterestCrossRefDao) {
    this.realEstateAgentDao = realEstateAgentDao;
    this.propertyDao = propertyDao;
    this.photoDao = photoDao;
    this.pointOfInterestDao = pointOfInterestDao;
    this.propertyPointOfInterestCrossRefDao = propertyPointOfInterestCrossRefDao;
  }

  @Override
  public LiveData<Property> getById(int propertyId) {
    // TODO REFACTORING
    final MutableLiveData<Property> propertyLiveData = new MutableLiveData<>();
    doInBackground.execute(
        () -> {
          propertyEntity = propertyDao.getById(propertyId);
          propertyEntity.setAgent(realEstateAgentDao.getById(propertyEntity.agentID));
        });

    final LiveData<List<PointOfInterestEntity>> pointOfInterestsLiveData =
        pointOfInterestDao.getPointOfInterestByPropertyId(propertyId);

    pointOfInterestsLiveData.observeForever(
        new Observer<List<PointOfInterestEntity>>() {

          @Override
          public void onChanged(List<PointOfInterestEntity> pointOfInterestEntities) {
            propertyEntity.setPointOfInterestNearby(
                pointOfInterestEntities.stream()
                    .map(PointOfInterestEntity::toModel)
                    .collect(Collectors.toList()));
            pointOfInterestsLiveData.removeObserver(this);

            final LiveData<List<PhotoEntity>> photoEntitiesLiveData =
                photoDao.getByPropertyId(propertyId);

            photoEntitiesLiveData.observeForever(
                new Observer<List<PhotoEntity>>() {

                  @Override
                  public void onChanged(List<PhotoEntity> photoEntities) {
                    propertyEntity.setPhotoList(
                        photoEntities.stream()
                            .map(PhotoEntity::toModel)
                            .collect(Collectors.toList()));
                    photoEntitiesLiveData.removeObserver(this);
                    propertyLiveData.setValue(propertyEntity);
                  }
                });
          }
        });
    return propertyLiveData;
  }

  @Override
  public LiveData<List<Property>> getAll() {
    if (allProperties == null) {
      allProperties = new MutableLiveData<>();
      realEstateAgentDao
          .getAllAgentWithProperties()
          .observeForever(
              agentWithProperties -> {
                final List<Property> properties = new ArrayList<>();

                for (RealEstateAgentWithProperties entry : agentWithProperties) {
                  properties.addAll(entry.toProperties());
                }

                allProperties.postValue(properties);
              });
    }
    return allProperties;
  }

  @Override
  public void update(Property updatedProperty) {
    propertyDao.update(new PropertyEntity(updatedProperty));

    final List<Property> updatedList =
        allProperties.getValue().stream()
            .map(
                currentPropertyInTheList -> {
                  if (currentPropertyInTheList.getId() == updatedProperty.getId()) {
                    return updatedProperty;
                  }
                  return currentPropertyInTheList;
                })
            .collect(Collectors.toList());

    allProperties.postValue(updatedList);
  }

  @Override
  public void delete(Property property) {
    propertyDao.delete(new PropertyEntity(property));
    allProperties.getValue().remove(property);
    allProperties.postValue(allProperties.getValue());
  }

  @Override
  public LiveData<Integer> create(Property property) {
    // TODO Refactor
    final MutableLiveData<Integer> liveId = new MutableLiveData<>();
    Executors.newSingleThreadExecutor()
        .execute(
            () -> {
              final PropertyEntity propertyEntity = new PropertyEntity(property);
              final int id = (int) propertyDao.create(propertyEntity)[0];
              liveId.postValue(id);
              property.setId(id);
              allProperties.getValue().add(property);
              allProperties.postValue(allProperties.getValue());
            });
    return liveId;
  }

  @Override
  public void associateWithPointOfInterest(int propertyId, int pointOfInterestId) {
    propertyPointOfInterestCrossRefDao.create(getAssociation(propertyId, pointOfInterestId));
  }

  @Override
  public void removePointOfInterestFromProperty(int propertyId, int pointOfInterestId) {
    propertyPointOfInterestCrossRefDao.delete(getAssociation(propertyId, pointOfInterestId));
  }

  private Relationships.PropertyPointOfInterestCrossRef getAssociation(
      int propertyId, int pointOfInterestId) {
    final Relationships.PropertyPointOfInterestCrossRef association =
        new Relationships.PropertyPointOfInterestCrossRef();
    association.pointOfInterestId = pointOfInterestId;
    association.propertyId = propertyId;
    return association;
  }
}
