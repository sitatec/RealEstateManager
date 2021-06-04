package com.berete.realestatemanager.domain.models;

public class Photo {

  private int id;
  private String url;
  private String description;
  private int propertyId;

  public Photo(){}

  public Photo(String url, String description) {
    this.url = url;
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(int propertyId) {
    this.propertyId = propertyId;
  }
}