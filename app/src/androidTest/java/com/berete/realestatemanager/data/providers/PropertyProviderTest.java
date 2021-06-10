package com.berete.realestatemanager.data.providers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.data.sources.local.Database;
import com.berete.realestatemanager.data.sources.local.adapters.PhotoDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.PointOfInterestDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.PropertyDataProvider;
import com.berete.realestatemanager.data.sources.local.adapters.RealEstateAgentDataProvider;
import com.berete.realestatemanager.data.sources.local.entities.PropertyEntity;
import com.berete.realestatemanager.di.SingletonModule;
import com.berete.realestatemanager.domain.models.Property;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;

@RunWith(AndroidJUnit4.class)
public class PropertyProviderTest {

  private static final PropertyEntity fakeProperty =
      new PropertyEntity(0,
          Property.Type.HOUSE,
          2.5,
          54.56,
          5,
          "desc",
          new PropertyEntity.AddressEntity(),
          false,
          464,
          3254,
          "");

  private ContentResolver contentResolver;
//  private Database inMemoryDb;
  private Uri contentUri;

  @Before
  public void setUp() {
    final Context context = ApplicationProvider.getApplicationContext();
//    inMemoryDb = Room.inMemoryDatabaseBuilder(context, Database.class).addCallback(new RoomDatabase.Callback() {
//      @Override
//      public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
//        super.onCreate(db);
//        inMemoryDb.getPropertyDao().create(fakeProperty);
//      }
//    }).build();

    contentResolver = context.getContentResolver();
    contentUri = Uri.parse("content://vnd."
        + context.getString(R.string.property_content_provider_authorities)
        + "/property");
  }

  @Test
  public void should_provide_property_by_id(){
    final Cursor result = contentResolver.query(ContentUris.withAppendedId(contentUri, 1),null, null, null, null);
    Assert.assertEquals(result.getCount(), 1);
  }


  // DI for testing //

//  @Module
//  @InstallIn(SingletonComponent.class)
//  public class TestModule {
//
//    @Provides
//    @Singleton
//    public Database provideDatabase() {
//      return inMemoryDb;
//    }
//
//    @Provides
//    @Singleton
//    public PropertyDataProvider providePropertyDataProvider() {
//      final Database database = Database.getInstance(ApplicationProvider.getApplicationContext());
//      return new PropertyDataProvider(
//          database.getRealEstateAgentDao(),
//          database.getPropertyDao(),
//          database.getPhotoDao(),
//          database.getPointOfInterestDao(),
//          database.getPropertyPointOfInterestCrossRefDao());
//    }
//
//    @Provides
//    @Singleton
//    public PhotoDataProvider providePhotoDataProvider() {
//      final Database database = Database.getInstance(ApplicationProvider.getApplicationContext());
//      return new PhotoDataProvider(database.getPhotoDao());
//    }
//
//    @Provides
//    @Singleton
//    public PointOfInterestDataProvider providePointOfInterestDataProvider() {
//      final Database database = Database.getInstance(ApplicationProvider.getApplicationContext());
//      return new PointOfInterestDataProvider(database.getPointOfInterestDao());
//    }
//
//    @Provides
//    @Singleton
//    public RealEstateAgentDataProvider provideAgentDataProvider() {
//      final Database database = Database.getInstance(ApplicationProvider.getApplicationContext());
//      return new RealEstateAgentDataProvider(database.getRealEstateAgentDao());
//    }
//
//  }

}