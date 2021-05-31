package com.berete.realestatemanager.domain.models;

public class Photo {

  private int id;
  private final String url;
  private final String description;

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

}