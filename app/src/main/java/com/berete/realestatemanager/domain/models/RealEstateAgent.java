package com.berete.realestatemanager.domain.models;

public class RealEstateAgent {

  private int id;
  private final String name;
  private String photoUrl;

  public RealEstateAgent(String name, String photoUrl) {
    this.name = name;
    this.photoUrl = photoUrl;
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

  public String getPhotoUrl() {
    return photoUrl;
  }
}
