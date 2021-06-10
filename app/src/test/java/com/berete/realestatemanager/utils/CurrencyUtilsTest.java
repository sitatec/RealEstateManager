package com.berete.realestatemanager.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyUtilsTest {

  @Test
  public void convertDoubleToCurrency() {
    assertEquals(CurrencyUtils.convertDoubleToCurrency(2000), "US$2,000");
  }

  @Test
  public void calculateLoan() {
    assertEquals(Math.rint(CurrencyUtils.calculateLoan(150_000, 0.001159, 20)), 626, 0);
  }

  @Test
  public void annualRateToMonthlyRate() {
    assertEquals(CurrencyUtils.annualRateToMonthlyRate(0.014), 0.001159, 0.001);
  }
}