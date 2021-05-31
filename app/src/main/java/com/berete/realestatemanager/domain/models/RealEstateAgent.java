package com.berete.realestatemanager.domain.models;

public class RealEstateAgent {

  private int id;
  private final String name;
  private final Photo photo;

  public RealEstateAgent(String name, Photo photo) {
    this.name = name;
    this.photo = photo;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Photo getPhoto() {
    return photo;
  }
}
