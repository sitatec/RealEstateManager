package com.berete.realestatemanager.data.sources.local;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.berete.realestatemanager.data.sources.local.adapters.PropertyDataProvider;
import com.berete.realestatemanager.data.sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data.sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data.sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.PropertyWithPhotosAndPointOfInterest;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.RealEstateAgentWithProperties;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.test_utils.LiveDataUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static com.berete.realestatemanager.test_utils.FakeData.fakeAgentEntity;
import static com.berete.realestatemanager.test_utils.FakeData.fakePointOfInterestEntity;
import static com.berete.realestatemanager.test_utils.FakeData.fakeProperty;
import static com.berete.realestatemanager.test_utils.FakeData.fakePropertyEntity;
import static com.berete.realestatemanager.test_utils.FakeData.fakePropertyPhotoEntity;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertyDataProviderTest {

  private RealEstateAgentDao realEstateAgentDao;
  private PropertyDao propertyDao;
  private PhotoDao photoDao;
  private PointOfInterestDao pointOfInterestDao;
  private PropertyDataProvider propertyDataProvider;
  private PropertyPointOfInterestCrossRefDao propertyPointOfInterestCrossRefDao;

  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  @Before
  public void setUp() {
    realEstateAgentDao = mock(RealEstateAgentDao.class);
    propertyDao = mock(PropertyDao.class);
    photoDao = mock(PhotoDao.class);
    pointOfInterestDao = mock(PointOfInterestDao.class);
    propertyPointOfInterestCrossRefDao = mock(PropertyPointOfInterestCrossRefDao.class);

    propertyDataProvider =
        new PropertyDataProvider(realEstateAgentDao, propertyDao, photoDao, pointOfInterestDao, propertyPointOfInterestCrossRefDao);

    setUpMocks();
  }

  private void setUpMocks() {
    when(propertyDao.getById(fakePropertyEntity.getId())).thenReturn(fakePropertyEntity);
    when(realEstateAgentDao.getById(fakeAgentEntity.getId())).thenReturn(fakeAgentEntity);
    when(pointOfInterestDao.getPointOfInterestByPropertyId(fakePropertyEntity.getId()))
        .thenReturn(new MutableLiveData<>(Collections.singletonList(fakePointOfInterestEntity)));
    when(photoDao.getByPropertyId(fakePropertyEntity.getId()))
        .thenReturn(new MutableLiveData<>(Collections.singletonList(fakePropertyPhotoEntity)));

    final PropertyWithPhotosAndPointOfInterest propertyWithPhotoAndPointOfInterest =
        new PropertyWithPhotosAndPointOfInterest();
    propertyWithPhotoAndPointOfInterest.property = fakePropertyEntity;
    propertyWithPhotoAndPointOfInterest.photos = Collections.singletonList(fakePropertyPhotoEntity);
    propertyWithPhotoAndPointOfInterest.pointOfInterests =
        Collections.singletonList(fakePointOfInterestEntity);

    final RealEstateAgentWithProperties agentWithProperties = new RealEstateAgentWithProperties();
    agentWithProperties.realEstateAgent = fakeAgentEntity;
    agentWithProperties.tempProperties =
        Collections.singletonList(propertyWithPhotoAndPointOfInterest);
    when(realEstateAgentDao.getAllAgentWithProperties())
        .thenReturn(Collections.singletonList(agentWithProperties));
  }

  @Test
  public void should_get_property_by_id() throws InterruptedException {
    final Property property = LiveDataUtils.getLiveDataValue(propertyDataProvider.getById(fakeProperty.getId()));

    // When fetching a property the PropertyDataProvider should fetch all data associated with the
    // property
    assertEquals(property.getAgent(), fakeAgentEntity);
    assertTrue(property.getPhotoList().contains(fakePropertyPhotoEntity));
    assertTrue(property.getPointOfInterestNearby().contains(fakePointOfInterestEntity));
  }

  @Test
  public void should_get_all_properties() throws InterruptedException {
    final Property property = LiveDataUtils.getLiveDataValue(propertyDataProvider.getAll()).get(0);

    // When fetching a property the PropertyDataProvider should fetch all data associated with the
    // property
    assertEquals(property.getPhotoList().get(0).getUrl(), fakePropertyPhotoEntity.getUrl());
    assertEquals(
        property.getPointOfInterestNearby().get(0).getName(), fakePointOfInterestEntity.getName());
    assertEquals(property.getAgent().getName(), fakeAgentEntity.getName());
  }

  @Test
  public void update() {
    propertyDataProvider.update(fakeProperty);
    // The update method transform internally the Property to a new PropertyEntity (
    // to for the compatibility with room), so we can't verify the exact argument, instead we have
    // to capture it and check the values of its fields
    final ArgumentCaptor<PropertyEntity> propertyCaptor =
        ArgumentCaptor.forClass(PropertyEntity.class);
    verify(propertyDao).update(propertyCaptor.capture());

    final PropertyEntity capturedArg = propertyCaptor.getValue();

    assertEquals(capturedArg.id, fakeProperty.getId());
    assertEquals(capturedArg.getDescription(), fakeProperty.getDescription());
    assertEquals(capturedArg.getPrice(), fakeProperty.getPrice(), 0);
  }

  @Test
  public void delete() {
    propertyDataProvider.delete(fakePropertyEntity);
    // The delete method transform internally the Property to a new PropertyEntity (
    // to for the compatibility with room), so we can't verify the exact argument, instead we have
    // to capture it and check the values of its fields
    final ArgumentCaptor<PropertyEntity> propertyCaptor =
        ArgumentCaptor.forClass(PropertyEntity.class);
    verify(propertyDao).delete(propertyCaptor.capture());

    final PropertyEntity capturedArg = propertyCaptor.getValue();

    assertEquals(capturedArg.id, fakeProperty.getId());
    assertEquals(capturedArg.getDescription(), fakeProperty.getDescription());
    assertEquals(capturedArg.getPrice(), fakeProperty.getPrice(), 0);
  }

  @Test
  public void create() {
    when(propertyDao.create(any())).thenReturn(new long[1]);
    propertyDataProvider.create(fakePropertyEntity);
    // The create method transform internally the Property to a new PropertyEntity (
    // to for the compatibility with room), so we can't verify the exact argument, instead we have
    // to capture it and check the values of its fields
    final ArgumentCaptor<PropertyEntity> propertyCaptor =
        ArgumentCaptor.forClass(PropertyEntity.class);
    verify(propertyDao).create(propertyCaptor.capture());

    final PropertyEntity capturedArg = propertyCaptor.getValue();

    assertEquals(capturedArg.id, fakeProperty.getId());
    assertEquals(capturedArg.getDescription(), fakeProperty.getDescription());
    assertEquals(capturedArg.getPrice(), fakeProperty.getPrice(), 0);
  }
}
