package com.berete.realestatemanager.data_sources.local.adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.berete.realestatemanager.data_sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data_sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data_sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data_sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data_sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data_sources.local.entities.Relationships;
import com.berete.realestatemanager.data_sources.local.entities.Relationships.RealEstateAgentWithProperties;
import com.berete.realestatemanager.domain.data_providers.PropertyProvider;
import com.berete.realestatemanager.domain.models.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PropertyDataProvider implements PropertyProvider {

  private final RealEstateAgentDao realEstateAgentDao;
  private final PropertyDao propertyDao;
  private final PhotoDao photoDao;
  private final PointOfInterestDao pointOfInterestDao;
  private final PropertyPointOfInterestCrossRefDao propertyPointOfInterestCrossRefDao;

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
  public Property getById(int propertyId) {
    final PropertyEntity propertyEntity = propertyDao.getById(propertyId);
    //    final RealEstateAgentEntity agentEntity =
    // realEstateAgentDao.getById(propertyEntity.agentID);
    //
    //    final List<PointOfInterestEntity> pointOfInterestEntities =
    //        pointOfInterestDao.getPointOfInterestByPropertyId(propertyId);
    //
    //    final List<PhotoEntity> photoEntities = photoDao.getByPropertyId(propertyId);
    //
    //    propertyEntity.setAgent(agentEntity);
    //    propertyEntity.setPhotoList(photoEntities);
    //    propertyEntity.setPointOfInterestNearby(pointOfInterestEntities);

    return propertyEntity;
  }

  @Override
  public List<Property> getAll() {
    final List<RealEstateAgentWithProperties> realEstateAgentWithProperties =
        realEstateAgentDao.getAllAgentWithProperties();
    final List<Property> properties = new ArrayList<>();

    for (RealEstateAgentWithProperties entry : realEstateAgentWithProperties) {
      properties.addAll(entry.toProperties());
    }

    return properties;
  }

  @Override
  public void update(Property property) {
    propertyDao.update(new PropertyEntity(property));
  }

  @Override
  public void delete(Property property) {
    propertyDao.delete(new PropertyEntity(property));
  }

  @Override
  public LiveData<Integer> create(Property property) {
    final MutableLiveData<Integer> liveId = new MutableLiveData<>();
    Executors.newSingleThreadExecutor()
        .execute(() -> liveId.postValue((int) propertyDao.create(new PropertyEntity(property))));
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
