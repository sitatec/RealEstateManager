package com.berete.realestatemanager.domain.models;

public class Photo {

  private final long id;
  private final String url;
  private final String description;

  public Photo(long id, String url, String description) {
    this.id = id;
    this.url = url;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

}