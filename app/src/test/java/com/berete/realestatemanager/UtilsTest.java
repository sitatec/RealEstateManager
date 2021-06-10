package com.berete.realestatemanager;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class UtilsTest {

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
}