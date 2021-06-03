package com.berete.realestatemanager.ui.edit;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.berete.realestatemanager.domain.models.Property;

import java.time.LocalDate;

import static com.berete.realestatemanager.domain.models.Property.PROPERTY_RELATED_DATE_FORMATTER;

public class PropertyDataBinding extends BaseObservable {

  private final Property property;

  public PropertyDataBinding(Property property) {
    this.property = property;
  }

  public Property getProperty() {
    return property;
  }

  @Bindable
  public String getType() {
    return property.getType().name();
  }

  public void setType(String type) {
    property.setType(Property.Type.valueOf(type));
  }

  @Bindable
  public String getPrice() {
    return Double.toString(property.getPrice());
  }

  public void setPrice(String price) {
    property.setPrice(Double.parseDouble(price));
  }

  @Bindable
  public String getSurface() {
    return String.valueOf(property.getSurface());
  }

  public void setSurface(String surface) {
    property.setSurface(Double.parseDouble(surface));
  }

  @Bindable
  public String getNumberOfRooms() {
    return String.valueOf(property.getNumberOfRooms());
  }

  public void setNumberOfRooms(String numberOfRooms) {
    property.setNumberOfRooms(Integer.parseInt(numberOfRooms));
  }

  @Bindable
  public String getDescription() {
    return property.getDescription();
  }

  public void setDescription(String description) {
    property.setDescription(description);
  }

  //  public List<Photo> getPhotoList() {
  //    return photoList;
  //  }
  //
  //  @SuppressWarnings("unchecked")
  //  public void setPhotoList(List<? extends Photo> photoList) {
  //    this.photoList = (List<Photo>) photoList;
  //  }

  @Bindable
  public String getFormattedAddress() {
    return property.getAddress().getFormattedAddress();
  }

  public void setFormattedAddress(String address) {
    property.getAddress().setFormattedAddress(address);
  }

  @Bindable
  public String getLocality() {
    return property.getAddress().getLocality();
  }

  public void setLocality(String locality) {
    property.getAddress().setLocality(locality);
  }

  //  public List<Property.PointOrInterest> getPointOfInterestNearby() {
  //    return  pointOfInterestNearby;
  //  }
  //
  //  @SuppressWarnings("unchecked")
  //  public void setPointOfInterestNearby(List<? extends Property.PointOrInterest>
  // pointOfInterestNearby) {
  //    this.pointOfInterestNearby = (List<Property.PointOrInterest>) pointOfInterestNearby;
  //  }

  @Bindable
  public boolean isSold() {
    return property.isSold();
  }

  public void setSold(boolean available) {
    property.setSold(available);
  }

  @Bindable
  public String getFormattedPublicationDate() {
    final LocalDate publicationDate = LocalDate.ofEpochDay(property.getPublicationDate());
    return publicationDate.format(PROPERTY_RELATED_DATE_FORMATTER);
  }

  public void setFormattedPublicationDate(String date) {
    property.setPublicationDate(
        LocalDate.parse(date, PROPERTY_RELATED_DATE_FORMATTER).toEpochDay());
  }

  @Bindable
  public String getSaleDate() {
    final LocalDate publicationDate = LocalDate.ofEpochDay(property.getSaleDate());
    return publicationDate.format(PROPERTY_RELATED_DATE_FORMATTER);
  }

  public void setSaleDate(String saleDate) {
    property.setPublicationDate(
        LocalDate.parse(saleDate, PROPERTY_RELATED_DATE_FORMATTER).toEpochDay());
  }

  @Bindable
  public String getAgentName() {
    return property.getAgent().getName();
  }

  public void setAgentName(String agentName) {
    property.getAgent().setName(agentName);
  }
}
