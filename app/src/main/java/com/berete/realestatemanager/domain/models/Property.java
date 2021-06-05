package com.berete.realestatemanager.domain.models;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Property {

  public static final DateTimeFormatter PROPERTY_RELATED_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  private int id;
  private Type type = Type.APARTMENT;
  private double price;
  private double surface;
  private int numberOfRooms;
  private String description;
  private List<Photo> photoList;
  private String mainPhotoUrl = "";
  private Address address;
  private List<PointOfInterest> pointOfInterestNearby;
  private boolean isSold;
  private long publicationDate;
  private long saleDate;
  private RealEstateAgent agent;

  public Property(){}

  public Property(
      Type type,
      double price,
      double surface,
      int numberOfRooms,
      String description,
      List<Photo> photoList,
      Address address,
      List<PointOfInterest> pointOfInterestNearby,
      boolean isSold,
      long publicationDate,
      long saleDate,
      RealEstateAgent agent) {
    this.type = type;
    this.price = price;
    this.surface = surface;
    this.numberOfRooms = numberOfRooms;
    this.description = description;
    this.photoList = photoList;
    this.address = address;
    this.pointOfInterestNearby = pointOfInterestNearby;
    this.isSold = isSold;
    this.publicationDate = publicationDate;
    this.saleDate = saleDate;
    this.agent = agent;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getSurface() {
    return surface;
  }

  public void setSurface(double surface) {
    this.surface = surface;
  }

  public int getNumberOfRooms() {
    return numberOfRooms;
  }

  public void setNumberOfRooms(int numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Photo> getPhotoList() {
    return photoList;
  }

  @SuppressWarnings("unchecked")
  public void setPhotoList(List<? extends Photo> photoList) {
    this.photoList = (List<Photo>) photoList;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<PointOfInterest> getPointOfInterestNearby() {
    return  pointOfInterestNearby;
  }

  @SuppressWarnings("unchecked")
  public void setPointOfInterestNearby(List<? extends PointOfInterest> pointOfInterestNearby) {
    this.pointOfInterestNearby = (List<PointOfInterest>) pointOfInterestNearby;
  }

  public String getMainPhotoUrl() {
    return mainPhotoUrl;
  }

  public void setMainPhotoUrl(String  mainPhotoUrl) {
    this.mainPhotoUrl = mainPhotoUrl;
  }

  public boolean isSold() {
    return isSold;
  }

  public void setSold(boolean sold) {
    isSold = sold;
  }

  public long getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(long availableSince) {
    this.publicationDate = availableSince;
  }

  public long getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(long saleDate) {
    this.saleDate = saleDate;
  }

  public RealEstateAgent getAgent() {
    return agent;
  }

  public void setAgent(RealEstateAgent agent) {
    this.agent = agent;
  }


  // ----------------------   INNERS  ----------------------- //

  public static class PointOfInterest {
    private int id;
    private final String name;

    public PointOfInterest(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }
  }

  public static class Address {
    private String locality;
    private String postalCode;
    private String formattedAddress;

    public Address(){}

    public Address(String locality, String postalCode, String formattedAddress) {
      this.locality = locality;
      this.postalCode = postalCode;
      this.formattedAddress = formattedAddress;
    }

    public String getLocality() {
      return locality;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public String getFormattedAddress() {
      return formattedAddress;
    }

    public void setLocality(String locality) {
      this.locality = locality;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    public void setFormattedAddress(String formattedAddress) {
      this.formattedAddress = formattedAddress;
    }
  }

  public enum Type {
    APARTMENT,
    LOFT,
    MANOR,
    PENTHOUSE,
    HOUSE;

    public static String[] names(){
      return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
  }
}
