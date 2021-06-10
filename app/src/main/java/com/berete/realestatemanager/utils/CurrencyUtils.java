package com.berete.realestatemanager.utils;

import java.text.NumberFormat;
import java.util.Currency;

public class CurrencyUtils {

  public static final int MOTH_OF_YEAR = 12;

  public static String convertDoubleToCurrency(double number) {
    final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    numberFormat.setMaximumFractionDigits(0);
    numberFormat.setCurrency(Currency.getInstance("USD"));
    return numberFormat.format(number);
  }

  public static double calculateLoan(double loanAmount, double totalMonthlyRate, int duration) {
    totalMonthlyRate = totalMonthlyRate / 100;
    return (loanAmount * totalMonthlyRate) / (1 - Math.pow((1 + totalMonthlyRate), -duration * 12));
  }

  public static double annualRateToMonthlyRate(double annuallyRate) {
    return Math.pow(1 + annuallyRate, (double) 1 / MOTH_OF_YEAR) - 1; // 12 -> month of year
  }
}
