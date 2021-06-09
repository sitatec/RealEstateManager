package com.berete.realestatemanager.data.sources.local;

import androidx.test.core.app.ApplicationProvider;

import com.berete.realestatemanager.data.sources.local.entities.Relationships;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.PropertyPointOfInterestCrossRef;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.RealEstateAgentWithProperties;
import com.berete.realestatemanager.data.sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data.sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data.sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.domain.models.Property;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntitiesRelationshipTest {

  private static final Property.Address fakeAddress =
      new Property.Address("locality", "postalCode", "formattedAddr");
  private static final PropertyEntity fakeProperty =
      new PropertyEntity(
          Property.Type.APARTMENT, 2.5, 54.56, 5, "desc", fakeAddress, false, 464, 3254);
  private static final PhotoEntity fakePropertyPhoto = new PhotoEntity("url", "d");
  private static final PointOfInterestEntity fakePointOfInterest = new PointOfInterestEntity("poi");
  private static final RealEstateAgentEntity fakeAgent =
      new RealEstateAgentEntity("Agent", "fakeAgentPhotoUrl");
  private static Database database;

  @BeforeClass
  public static void setUp() {
    fakeProperty.agentID = 1;
    database = Database.getTestInstance(ApplicationProvider.getApplicationContext());

    database.getRealEstateAgentDao().create(fakeAgent);

    fakePropertyPhoto.propertyId = 1;
    database.getPhotoDao().create(fakePropertyPhoto);

    database.getPropertyDao().create(fakeProperty);

    database.getPointOfInterestDao().create(fakePointOfInterest);

    final PropertyPointOfInterestCrossRef propertyPointOfInterestCrossRef =
        new PropertyPointOfInterestCrossRef();
    propertyPointOfInterestCrossRef.pointOfInterestId = 1;
    propertyPointOfInterestCrossRef.propertyId = 1;
    database.getPropertyPointOfInterestCrossRefDao().create(propertyPointOfInterestCrossRef);
  }

  @Test
  public void returned_entities_should_match_the_defined_relationship() {
    final List<RealEstateAgentWithProperties> agentWithProperties =
        database.getRealEstateAgentDao().getAllAgentWithProperties();
    final List<Relationships.PropertyWithPhotosAndPointOfInterest> propertiesFromDb =
        agentWithProperties.get(0).tempProperties;
    final RealEstateAgentEntity agentFromDb = agentWithProperties.get(0).realEstateAgent;

    // AGENT
    assertEquals(fakeAgent.getName(), agentFromDb.getName());

    // PROPERTY
    assertEquals(propertiesFromDb.get(0).property.getDescription(), fakeProperty.getDescription());
    assertEquals(
        propertiesFromDb.get(0).pointOfInterests.get(0).getName(), fakePointOfInterest.getName());
    assertEquals(propertiesFromDb.get(0).photos.get(0).getUrl(), fakePropertyPhoto.getUrl());
  }

  @Test
  public void entities_from_the_database_should_contain_embedded_Objects() {
    final List<RealEstateAgentWithProperties> agentWithProperties =
        database.getRealEstateAgentDao().getAllAgentWithProperties();
    final List<Relationships.PropertyWithPhotosAndPointOfInterest> properties =
        agentWithProperties.get(0).tempProperties;

    assertEquals(
        properties.get(0).property.getAddress().getLocality(),
        fakeProperty.getAddress().getLocality());
    assertEquals(fakeAgent.getPhotoUrl(), agentWithProperties.get(0).realEstateAgent.getPhotoUrl());
  }
}
