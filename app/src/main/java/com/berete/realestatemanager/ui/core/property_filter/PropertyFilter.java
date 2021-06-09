package com.berete.realestatemanager.ui.core.property_filter;

import com.berete.realestatemanager.domain.models.Property;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyFilter {

  private FilterData filterData;
  private Stream<Property> propertyStream;
  private boolean filterInitialised = false;

  public PropertyFilter initFilter(FilterData filterData) {
    this.filterData = filterData;
    propertyStream = filterData.getPropertyList().stream();
    filterInitialised = true;
    return this;
  }

  public PropertyFilter filterByMinMaxSurface() {
    assert filterInitialised;
    propertyStream =
        propertyStream.filter(
            property -> {
              if (filterData.getMaxSurface() != 0) {
                return property.getSurface() < filterData.getMaxSurface()
                    && property.getSurface() > filterData.getMinSurface();
              }
              return property.getSurface() > filterData.getMinSurface();
            });
    return this;
  }

  public PropertyFilter filterByMinMaxPrice() {
    assert filterInitialised;
    propertyStream =
        propertyStream.filter(
            property -> {
              if (filterData.getMaxPrice() != 0) {
                return property.getPrice() < filterData.getMaxPrice()
                    && property.getPrice() > filterData.getMinPrice();
              }
              return property.getPrice() > filterData.getMinPrice();
            });
    return this;
  }

  public PropertyFilter filterByPublishedLessThanXWeeks() {
    assert filterInitialised;
    if (filterData.getNumberOfWeeks() != 0) {
      propertyStream =
          propertyStream.filter(
              property ->
                  LocalDate.ofEpochDay(property.getPublicationDate())
                      .isAfter(LocalDate.now().minusWeeks(filterData.getNumberOfWeeks())));
    }
    return this;
  }

  public PropertyFilter filterBySoldLastXMonths() {
    assert filterInitialised;
    if (filterData.getNumberOfMonths() != 0) {
      propertyStream =
          propertyStream.filter(
              property ->
                  LocalDate.ofEpochDay(property.getSaleDate())
                      .isAfter(LocalDate.now().minusMonths(filterData.getNumberOfMonths())));
    }
    return this;
  }

  public PropertyFilter filterByPointOfInterest() {
    assert filterInitialised;
    if (!filterData.getPointOfInterestSet().isEmpty()) {
      propertyStream =
          propertyStream.filter(
              property ->
                  property
                      .getPointOfInterestNearby()
                      .containsAll(filterData.getPointOfInterestSet()));
    }
    return this;
  }

  public PropertyFilter filterByMinPhotos() {
    assert filterInitialised;
    if (filterData.getMinPhotos() != 0) {
      propertyStream =
          propertyStream.filter(
              property -> {
                if (property.getPhotoList().isEmpty()) {
                  return true;
                }
                return property.getPhotoList().size() > filterData.getMinPhotos();
              });
    }
    return this;
  }

  public PropertyFilter filterByLocality() {
    assert filterInitialised;
    if (!filterData.getLocality().isEmpty()) {
      propertyStream =
          propertyStream.filter(
              property -> property.getAddress().getLocality().equals(filterData.getLocality()));
    }
    return this;
  }

  public List<Property> apply() {
    assert filterInitialised;
    return propertyStream.collect(Collectors.toList());
  }
}
