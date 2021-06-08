package com.berete.realestatemanager;

import com.berete.realestatemanager.data.sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data.sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data.sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.domain.models.Photo;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import java.util.Collections;

public interface FakeData {
   Photo fakePhoto = new Photo("url", "desc");
   RealEstateAgent fakeAgent = new RealEstateAgent("name", "PhotoUrl");
   Property.Address fakeAddress = new Property.Address("locality", "postalCode", "formattedAddr");
   Property fakeProperty =
      new Property(
          Property.Type.HOUSE,
          2.5,
          54.56,
          5,
          "desc",
          Collections.singletonList(fakePhoto),
          fakeAddress,
          Collections.singletonList(new Property.PointOfInterest("name")),
          false,
          464,
          3254,
          fakeAgent);


   Property.Address fakeAddressEntity =
       new Property.Address("locality", "postalCode", "formattedAddr");
   PropertyEntity fakePropertyEntity =
       new PropertyEntity(
           Property.Type.APARTMENT, 2.5, 54.56, 5, "desc", fakeAddressEntity, false, 464, 3254);
   PhotoEntity fakePropertyPhotoEntity = new PhotoEntity("url", "d");
   PointOfInterestEntity fakePointOfInterestEntity = new PointOfInterestEntity("poi");
   RealEstateAgentEntity fakeAgentEntity =
       new RealEstateAgentEntity("Agent", "fakeAgentPhotoUrl");
}
