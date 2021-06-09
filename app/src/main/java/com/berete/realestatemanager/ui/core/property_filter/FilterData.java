package com.berete.realestatemanager.ui.core.property_filter;

import com.berete.realestatemanager.domain.models.Property;

import java.util.List;
import java.util.Set;

public class FilterData {

  private int numberOfWeeks;
  private int numberOfMonths;
  private double minSurface;
  private double minPrice;
  private double maxSurface;
  private double maxPrice;
  private int minPhotos;
  private String locality;
  private Set<Property.PointOfInterest> pointOfInterestSet;
  private List<Property> propertyList;

  public FilterData() {}

  public FilterData(
      int numberOfWeeks,
      int numberOfMonths,
      double minSurface,
      double minPrice,
      double maxSurface,
      double maxPrice,
      int minPhotos, Set<Property.PointOfInterest> pointOfInterestSet,
      List<Property> propertyList) {
    this.numberOfWeeks = numberOfWeeks;
    this.numberOfMonths = numberOfMonths;
    this.minSurface = minSurface;
    this.minPrice = minPrice;
    this.maxSurface = maxSurface;
    this.maxPrice = maxPrice;
    this.minPhotos = minPhotos;
    this.pointOfInterestSet = pointOfInterestSet;
    this.propertyList = propertyList;
  }

  public int getNumberOfWeeks() {
    return numberOfWeeks;
  }

  public void setNumberOfWeeks(int numberOfWeeks) {
    this.numberOfWeeks = numberOfWeeks;
  }

  public int getNumberOfMonths() {
    return numberOfMonths;
  }

  public void setNumberOfMonths(int numberOfMonths) {
    this.numberOfMonths = numberOfMonths;
  }

  public double getMinSurface() {
    return minSurface;
  }

  public void setMinSurface(double minSurface) {
    this.minSurface = minSurface;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
  }

  public double getMaxSurface() {
    return maxSurface;
  }

  public void setMaxSurface(double maxSurface) {
    this.maxSurface = maxSurface;
  }

  public double getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(double maxPrice) {
    this.maxPrice = maxPrice;
  }

  public Set<Property.PointOfInterest> getPointOfInterestSet() {
    return pointOfInterestSet;
  }

  public void setPointOfInterestSet(Set<Property.PointOfInterest> pointOfInterestSet) {
    this.pointOfInterestSet = pointOfInterestSet;
  }

  public List<Property> getPropertyList() {
    return propertyList;
  }

  public void setPropertyList(List<Property> propertyList) {
    this.propertyList = propertyList;
  }

  public int getMinPhotos() {
    return minPhotos;
  }

  public void setMinPhotos(int minPhotos) {
    this.minPhotos = minPhotos;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }
}
