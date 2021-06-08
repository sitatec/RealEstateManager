package com.berete.realestatemanager.data.sources.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.berete.realestatemanager.data.sources.local.dao.PhotoDao;
import com.berete.realestatemanager.data.sources.local.dao.PointOfInterestDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyDao;
import com.berete.realestatemanager.data.sources.local.dao.PropertyPointOfInterestCrossRefDao;
import com.berete.realestatemanager.data.sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data.sources.local.entities.PhotoEntity;
import com.berete.realestatemanager.data.sources.local.entities.PointOfInterestEntity;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.data.sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.data.sources.local.entities.Relationships.PropertyPointOfInterestCrossRef;
import com.berete.realestatemanager.domain.models.Property;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.Executors;

@androidx.room.Database(
    entities = {
      PropertyEntity.class,
      PhotoEntity.class,
      RealEstateAgentEntity.class,
      PointOfInterestEntity.class,
      PropertyPointOfInterestCrossRef.class
    },
    version = 1,
    exportSchema = false)
public abstract class Database extends RoomDatabase {

  private static Database instance;

  public abstract PropertyDao getPropertyDao();

  public abstract RealEstateAgentDao getRealEstateAgentDao();

  public abstract PhotoDao getPhotoDao();

  public abstract PointOfInterestDao getPointOfInterestDao();

  public abstract PropertyPointOfInterestCrossRefDao getPropertyPointOfInterestCrossRefDao();

  public static synchronized Database getInstance(Context context) {
    if (instance == null) {
      synchronized (Database.class) {
        instance =
            Room.databaseBuilder(context, Database.class, "real_estate_manager.db")
                .addCallback(prepopulate)
                .build();
      }
    }
    return instance;
  }

  public static Database getTestInstance(Context context) {
    return Room.inMemoryDatabaseBuilder(context, Database.class).build();
  }

  private static final Callback prepopulate =
      new Callback() {
        @Override
        public void onCreate(@NotNull SupportSQLiteDatabase db) {
          super.onCreate(db);

          Executors.newSingleThreadExecutor()
              .execute(
                  () -> {
                    // AGENTS
                    instance
                        .getRealEstateAgentDao()
                        .create(
                            new RealEstateAgentEntity(
                                "Moussa Barry", "file:///android_asset/agent.jpeg"),
                            new RealEstateAgentEntity(
                                "Ibrahim Sorry", "file:///android_asset/agent_1.jpeg"),
                            new RealEstateAgentEntity(
                                "Alex Mason", "file:///android_asset/agent_2.jpeg"),
                            new RealEstateAgentEntity(
                                "Alexa Mason", "file:///android_asset/agent_3.jpeg"));

                    // PROPERTIES
                    final int numberOfProperty = 7;

                    final PropertyEntity property1 = getPropertyFixturesModel();
                    property1.agentID = 2;
                    property1.setType(Property.Type.HOUSE);

                    final PropertyEntity property2 = getPropertyFixturesModel();
                    property2.agentID = 4;
                    property2.setType(Property.Type.APARTMENT);
                    property2.setPrice(6_000_000);
                    property2.address.setLocality("Manhattan");
                    property2.address.setFormattedAddress("1360 Rue Wellington S, Sherbrooke, QC ");
                    property2.setSold(true);
                    property2.setPublicationDate(
                        LocalDate.now().minusMonths(4).toEpochDay());
                    property2.setSaleDate(
                        LocalDate.now().minusMonths(2).toEpochDay());
                    property2.setMainPhotoUrl(
                        "https://images.unsplash.com/photo-1613545325278-f24b0cae1224?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGhvbWUlMjBpbnRlcmlvcnxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80");

                    final PropertyEntity property3 = getPropertyFixturesModel();
                    property3.agentID = 3;
                    property3.setPrice(42_000_000);
                    property3.setType(Property.Type.PENTHOUSE);
                    property3.address.setFormattedAddress("1095 Rue Galt O, Sherbrooke, QC");
                    property3.setMainPhotoUrl(
                        "https://www.interiorzine.com/wp-content/uploads/2014/12/symbolic-inner-courtyard-skylights.jpg");

                    final PropertyEntity property4 = getPropertyFixturesModel();
                    property4.agentID = 4;
                    property4.setType(Property.Type.LOFT);
                    property4.address.setLocality("Chicoutimi");
                    property4.address.setFormattedAddress("350 Rue Belvédère S, Sherbrooke, QC");
                    property4.setPrice(11_000_000);
                    property4.setMainPhotoUrl(
                        "https://images.unsplash.com/photo-1568605114967-8130f3a36994?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aG91c2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80");

                    final PropertyEntity property5 = getPropertyFixturesModel();
                    property5.agentID = 4;
                    property5.setPrice(9_000_000);
                    property5.address.setLocality("Labe");
                    property5.address.setFormattedAddress("90 Rue de l'Ontario, Sherbrooke, QC");
                    property5.setSold(true);
                    property5.setPublicationDate(
                        LocalDate.now().minusMonths(10).toEpochDay());
                    property5.setSaleDate(
                        LocalDate.now().minusMonths(1).toEpochDay());
                    property5.setMainPhotoUrl(
                        "https://www.architecturalrecord.com/ext/resources/Issues/2019/04-April/Record-Houses/1904-Gallery-House-Chicago-John-Ronan-Architects-01.jpg?t=1553532781&width=900");

                    final PropertyEntity property6 = getPropertyFixturesModel();
                    property6.agentID = 1;
                    property6.setType(Property.Type.HOUSE);
                    property6.setPrice(21_000_000);
                    property6.address.setLocality("Manhattan");
                    property6.address.setFormattedAddress("339 Rue King E, Sherbrooke, QC");
                    property6.setPublicationDate(
                        LocalDate.now().minusMonths(7).toEpochDay());
                    property6.setSaleDate(
                        LocalDate.now().minusMonths(3).toEpochDay());
                    property6.setSold(true);
                    property6.setMainPhotoUrl(
                        "https://www.opendoor.com/w/wp-content/uploads/2019/06/cost-selling-house-houston.jpg");

                    final PropertyEntity property7 = getPropertyFixturesModel();
                    property7.agentID = 3;
                    property7.setPrice(33_000_000);
                    property7.setType(Property.Type.MANOR);
                    property7.address.setFormattedAddress("825 Rue Bowen S, Sherbrooke, QC");
                    property7.setMainPhotoUrl(
                        "https://images.adsttc.com/media/images/5e1d/02c3/3312/fd58/9c00/06e9/large_jpg/NewHouse_SA_Photo_01.jpg?1578959519");

                    instance
                        .getPropertyDao()
                        .create(
                            property1, property2, property6, property3, property4, property5,
                            property7);

                    // PHOTOS
                    final PhotoDao photoDao = instance.getPhotoDao();
                    for (int propertyId = 1; propertyId <= numberOfProperty; propertyId++) {
                      photoDao.create(
                          new PhotoEntity(
                              "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F1026205392%2F0x0.jpg",
                              "Facade", propertyId),
                          new PhotoEntity(
                              "https://www.interiorzine.com/wp-content/uploads/2014/12/symbolic-inner-courtyard-skylights.jpg",
                              "Staircase",
                              propertyId),
                          new PhotoEntity(
                              "https://images.unsplash.com/photo-1568605114967-8130f3a36994?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aG91c2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=600&q=80",
                              "Lighting",
                              propertyId),
                          new PhotoEntity(
                              "https://cdn.vox-cdn.com/thumbor/frFQQhOsxl8DctGjkR8OLHpdKMs=/0x0:3686x2073/1200x800/filters:focal(1549x743:2137x1331)/cdn.vox-cdn.com/uploads/chorus_image/image/68976842/House_Tour_Liverman_3D6A3138_tour.0.jpg",
                              "Facade at the morning",
                              propertyId),
                          new PhotoEntity(
                              "https://images.unsplash.com/photo-1613545325278-f24b0cae1224?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGhvbWUlMjBpbnRlcmlvcnxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=600&q=80",
                              "Living room", propertyId),
                          new PhotoEntity(
                              "https://images.adsttc.com/media/images/5e1d/02c3/3312/fd58/9c00/06e9/large_jpg/NewHouse_SA_Photo_01.jpg?1578959519",
                              "Modern Architecture",
                              propertyId),
                          new PhotoEntity(
                              "https://www.opendoor.com/w/wp-content/uploads/2019/06/cost-selling-house-houston.jpg",
                              "House Facade",
                              propertyId),
                          new PhotoEntity(
                              "https://www.architecturalrecord.com/ext/resources/Issues/2019/04-April/Record-Houses/1904-Gallery-House-Chicago-John-Ronan-Architects-01.jpg?t=1553532781&width=600",
                              "Architecture",
                              propertyId));
                    }

                    // POINT_OF_INTEREST
                    final int numberOfPointOfInterest = 4;
                    instance
                        .getPointOfInterestDao()
                        .create(
                            new PointOfInterestEntity("School"),
                            new PointOfInterestEntity("Restaurant"),
                            new PointOfInterestEntity("Hotel"),
                            new PointOfInterestEntity("Hospital"));

                    // POINT_OF_INTEREST - PROPERTY ASSOCIATION
                    final PropertyPointOfInterestCrossRefDao propertyPOIAssocDao =
                        instance.getPropertyPointOfInterestCrossRefDao();
                    int numberOfPOIByProperty;
                    PropertyPointOfInterestCrossRef propertyPOIAssocTable =
                        new PropertyPointOfInterestCrossRef();
                    int currentPointOfInterestId;

                    for (int propertyId = 1; propertyId <= numberOfProperty; propertyId++) {

                      propertyPOIAssocTable.propertyId = propertyId;
                      numberOfPOIByProperty = new Random().nextInt(numberOfPointOfInterest);

                      while (numberOfPOIByProperty-- > 0) {
                        currentPointOfInterestId = new Random().nextInt(numberOfPointOfInterest);
                        propertyPOIAssocTable.pointOfInterestId = currentPointOfInterestId;
                        propertyPOIAssocDao.create(propertyPOIAssocTable);
                      }
                    }
                  });
        }
      };

  private static PropertyEntity getPropertyFixturesModel() {
    return new PropertyEntity(
        0,
        Property.Type.APARTMENT,
        18_000_000,
        70,
        6,
        lorem,
        new PropertyEntity.AddressEntity(
            "Sherbrooke", "POS TAL", "Ruelle Alphonso-crouteau, Sherbrooke ,QC , Canada"),
        false,
        LocalDate.now().toEpochDay(),
        0,
        "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F1026205392%2F0x0.jpg");
  }

  private static final String lorem =
      "Le lorem ipsum est, en imprimerie, une suite de mots sans "
          + "signification utilisée à titre provisoire pour calibrer une mise en page, le texte définitif"
          + " venant remplacer le faux-texte dès qu'il est prêt ou que la mise en page est achevée. Généralement,"
          + " on utilise un texte en faux latin, le Lorem ipsum ou Lipsum."
          + " venant remplacer le faux-texte dès qu'il est prêt ou que la mise en page est achevée. Généralement,"
          + " on utilise un texte en faux latin, le Lorem ipsum ou Lipsum.";
}
