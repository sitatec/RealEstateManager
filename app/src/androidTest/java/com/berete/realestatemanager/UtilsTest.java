package com.berete.realestatemanager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UtilsTest {

  @Test
  public void isInternetAvailable() {
    assertEquals(isGoogleReachable(), Utils.isInternetAvailable(ApplicationProvider.getApplicationContext()));
  }

  private boolean isGoogleReachable(){
    try {
      return Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8").waitFor() == 0;
    } catch (Exception e) {
      return false;
    }
  }

}