package com.berete.realestatemanager.ui.loan_calculator;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.berete.realestatemanager.BR;
import com.berete.realestatemanager.utils.CurrencyUtils;

public class LoanCalculatorDataBinding extends BaseObservable {
  // DEFAULTS
  private double loanAmount = 300_000;
  private int duration = 20;
  private double interestRate = 0.0106;
  private double assuranceRate = 0.0034;
  private boolean isAnnualInterestRate = true;
  private boolean isAnnualAssuranceRate = true;

  @Bindable
  public String getMonthlyPayment() {
    double monthlyInterestRate = interestRate;
    double monthlyAssuranceRate = assuranceRate;
    if (isAnnualAssuranceRate) {
      monthlyAssuranceRate = CurrencyUtils.annualRateToMonthlyRate(assuranceRate);
    }
    if (isAnnualInterestRate) {
      monthlyInterestRate = CurrencyUtils.annualRateToMonthlyRate(interestRate);
    }
    final double totalMonthlyRate = monthlyAssuranceRate + monthlyInterestRate;
    final double monthlyPayment = CurrencyUtils.calculateLoan(loanAmount, totalMonthlyRate, duration);
    return CurrencyUtils.convertDoubleToCurrency(monthlyPayment);
  }

  //  public void setMonthlyPayment(String monthlyPayment){
  //    this.monthlyPayment = Double.parseDouble(monthlyPayment);
  //  }

  @Bindable
  public String getLoanAmount() {
    return String.valueOf(loanAmount);
  }

  public void setLoanAmount(String loanAmount) {
    this.loanAmount = Double.parseDouble(loanAmount);
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getDuration() {
    return String.valueOf(duration);
  }

  public void setDuration(String duration) {
    this.duration = Integer.parseInt(duration);
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getInterestRate() {
    return String.valueOf(interestRate);
  }

  public void setInterestRate(String interestRate) {
    this.interestRate = Double.parseDouble(interestRate);
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getAssuranceRate() {
    return String.valueOf(assuranceRate);
  }

  public void setAssuranceRate(String assuranceRate) {
    this.assuranceRate = Double.parseDouble(assuranceRate);
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public boolean isAnnualInterestRate() {
    return isAnnualInterestRate;
  }

  public void setAnnualInterestRate(boolean annualInterestRate) {
    isAnnualInterestRate = annualInterestRate;
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public boolean isAnnualAssuranceRate() {
    return isAnnualAssuranceRate;
  }

  public void setAnnualAssuranceRate(boolean annualAssuranceRate) {
    isAnnualAssuranceRate = annualAssuranceRate;
    notifyPropertyChanged(BR.monthlyPayment);
  }
}
