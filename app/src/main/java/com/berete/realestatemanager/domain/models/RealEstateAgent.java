package com.berete.realestatemanager.domain.models;

public class RealEstateAgent {

  private final long id;
  private final String name;
  private final Photo photo;

  public RealEstateAgent(long id, String name, Photo photo) {
    this.id = id;
    this.name = name;
    this.photo = photo;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Photo getPhoto() {
    return photo;
  }
}
