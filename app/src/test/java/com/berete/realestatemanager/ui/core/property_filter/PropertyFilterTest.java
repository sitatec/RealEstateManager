package com.berete.realestatemanager.ui.core.property_filter;

import com.berete.realestatemanager.domain.models.Photo;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.domain.models.Property.Type;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class PropertyFilterTest {

  final PropertyFilter propertyFilter = new PropertyFilter();
  final List<Property> propertyList = new ArrayList<>();
  FilterData filterData;

  final String[] fakePointOfInterests = new String[] {"school", "restaurant", "hospital", "another", "just", "for", "test"};

  @Before
  public void setUp() {
    final Random random = new Random();
    final List<Photo> photoList = new ArrayList<>();
    final List<Property.PointOfInterest> pointOfInterestList = new ArrayList<>();
    Property property;
    for (int i = 0; i < 50; i++) {
      property =
          new Property(
              Type.values()[random.nextInt(Type.values().length)],
              random.nextInt(100_000_000),
              random.nextInt(200),
              i,
              "description",
              new ArrayList<>(),
              new Property.Address(String.valueOf(random.nextInt(100_000_000)), "", ""),
              new ArrayList<>(),
              random.nextBoolean(),
              i < 25
                  ? LocalDate.now().minusWeeks(i).toEpochDay()
                  : LocalDate.now().minusDays(i).toEpochDay(),
              i < 25
                  ? LocalDate.now().minusMonths(i).toEpochDay()
                  : LocalDate.now().minusWeeks(i).toEpochDay(),
              new RealEstateAgent("name", "photoUrl"));

      // PHOTOS
      photoList.clear();
      for (int j = 0; j < random.nextInt(20); j++) {
        photoList.add(new Photo());
      }
      property.setPhotoList(photoList);

      // Point of Interest
      pointOfInterestList.clear();
      for (int k = 0; k < 10; k++) {
        pointOfInterestList.add(new Property.PointOfInterest(fakePointOfInterests[random.nextInt(fakePointOfInterests.length)]));
      }
      property.setPointOfInterestNearby(pointOfInterestList);

      propertyList.add(property);
    }

    filterData = new FilterData();
    filterData.setPropertyList(propertyList);
  }

  @Test
  public void filterByMinMaxSurface() {
    final double expectedMinSurface = 50;
    final double expectedMaxSurface = 170;
    filterData.setMinSurface(expectedMinSurface);
    filterData.setMaxSurface(expectedMaxSurface);
    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByMinMaxSurface().apply();
    boolean resultMatchTheFilter = true;
    for (Property property : filteredProperties) {
      resultMatchTheFilter =
          property.getSurface() < expectedMaxSurface && property.getSurface() > expectedMinSurface;
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterByMinMaxPrice() {
    final double expectedMinPrice = 90_000;
    final double expectedMaxPrice = 50_000_000;
    filterData.setMinPrice(expectedMinPrice);
    filterData.setMaxPrice(expectedMaxPrice);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByMinMaxPrice().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter =
          property.getPrice() < expectedMaxPrice && property.getPrice() > expectedMinPrice;
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterByPublishedLessThanXWeeks() {
    final int expectedMaxWeeks = new Random().nextInt(10);
    filterData.setNumberOfWeeks(expectedMaxWeeks);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByPublishedLessThanXWeeks().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter =
          LocalDate.ofEpochDay(property.getPublicationDate())
              .isAfter(LocalDate.now().minusWeeks(expectedMaxWeeks));
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterBySoldLastXMonths() {
    final int expectedMaxMoths = new Random().nextInt(10);
    filterData.setNumberOfMonths(expectedMaxMoths);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterBySoldLastXMonths().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter =
          LocalDate.ofEpochDay(property.getSaleDate())
              .isAfter(LocalDate.now().minusMonths(expectedMaxMoths));
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterByPointOfInterest() {
    final Set<Property.PointOfInterest> expectedPointOfInterest = new HashSet<>();
    expectedPointOfInterest.add(new Property.PointOfInterest("school"));
    expectedPointOfInterest.add(new Property.PointOfInterest("restaurant"));
    expectedPointOfInterest.add(new Property.PointOfInterest("hospital"));
    filterData.setPointOfInterestSet(expectedPointOfInterest);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByPointOfInterest().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter = property.getPointOfInterestNearby().containsAll(expectedPointOfInterest);
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterByMinPhotos() {
    final int expectedMinPhoto = getRandomProperty().getPhotoList().size();
    filterData.setMinPhotos(expectedMinPhoto);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByMinPhotos().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter = property.getPhotoList().size() > expectedMinPhoto;
    }
    assertTrue(resultMatchTheFilter);
  }

  @Test
  public void filterByLocality() {
    final String expectedLocality = getRandomProperty().getAddress().getLocality();
    filterData.setLocality(expectedLocality);

    final List<Property> filteredProperties =
        propertyFilter.initFilter(filterData).filterByLocality().apply();
    boolean resultMatchTheFilter = true;

    for (Property property : filteredProperties) {
      resultMatchTheFilter = property.getAddress().getLocality().equals(expectedLocality);
    }
    assertTrue(resultMatchTheFilter);

  }

  //  ------------  UTILS -------------- //

  public Property getRandomProperty() {
    return propertyList.get(new Random().nextInt(propertyList.size()));
  }
}
