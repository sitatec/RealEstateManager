package com.berete.realestatemanager;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;
import androidx.test.core.app.ApplicationProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class UtilsTest {
  ShadowConnectivityManager shadowConnectivityManager;
  @Before
  public void setUp() {
    final ConnectivityManager connectivityManager =
        (ConnectivityManager)
            ApplicationProvider.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
  }

  @Test
  public void convertDollarToEuro() {
    assertEquals(Utils.convertDollarToEuro(100), 81);
  }

  @Test
  public void convertEuroToDollar() {
    assertEquals(Utils.convertEuroToDollar(81), 100);
  }

  @Test
  public void getTodayDate() {
    assertEquals(Utils.getTodayDate(), new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
  }


  @Test
  public void internet_should_be_available() {
    enableConnectivity();
    assertTrue(Utils.isInternetAvailable(ApplicationProvider.getApplicationContext()));
  }

  @Test
  public void internet_should_not_be_available(){
    disableConnectivity();
    assertFalse(Utils.isInternetAvailable(ApplicationProvider.getApplicationContext()));
  }

  // ---- UTILS ---- //

  private void enableConnectivity() {
    shadowConnectivityManager.setActiveNetworkInfo(
        ShadowNetworkInfo.newInstance(
            NetworkInfo.DetailedState.CONNECTED,
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_WIFI,
            true,
            NetworkInfo.State.CONNECTED));
  }

  private void disableConnectivity() {
    shadowConnectivityManager.setActiveNetworkInfo(
        ShadowNetworkInfo.newInstance(
            NetworkInfo.DetailedState.DISCONNECTED,
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_WIFI,
            false,
            NetworkInfo.State.DISCONNECTED));
  }
}