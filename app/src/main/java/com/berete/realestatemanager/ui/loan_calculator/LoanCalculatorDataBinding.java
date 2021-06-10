package com.berete.realestatemanager.ui.loan_calculator;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.berete.realestatemanager.BR;
import com.berete.realestatemanager.utils.CurrencyUtils;

import java.text.NumberFormat;

public class LoanCalculatorDataBinding extends BaseObservable {
  // DEFAULTS
  private double loanAmount = 300_000;
  private int duration = 20;
  private double interestRate = 1.06;
  private double assuranceRate = 0.34;
  private boolean isAnnualInterestRate = true;
  private boolean isAnnualAssuranceRate = true;
  private double contributionRate = 13;

  @Bindable
  public String getMonthlyPayment() {
    double _interestRate = interestRate;
    double _assuranceRate = assuranceRate;
    if (isAnnualAssuranceRate) {
      _assuranceRate = CurrencyUtils.annualRateToMonthlyRate(_assuranceRate);
    }
    if (isAnnualInterestRate) {
      _interestRate = CurrencyUtils.annualRateToMonthlyRate(_interestRate);
    }
    final double totalMonthlyRate = _assuranceRate + _interestRate;
    final double monthlyPayment =
        CurrencyUtils.calculateLoan(loanAmount, totalMonthlyRate, duration);
    return CurrencyUtils.convertDoubleToCurrency(monthlyPayment);
  }

  @Bindable
  public String getContribution() {
    return CurrencyUtils.convertDoubleToCurrency(loanAmount * (contributionRate / 100));
  }

  @Bindable
  public String getLoanAmount() {
    return String.valueOf(loanAmount);
  }

  public void setLoanAmount(String loanAmount) {
    if (loanAmount.isEmpty()) {
      this.loanAmount = 0;
    } else {
      this.loanAmount = Double.parseDouble(loanAmount);
    }
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getDuration() {
    return String.valueOf(duration);
  }

  public void setDuration(String duration) {
    if (duration.isEmpty()) {
      this.duration = 0;
    } else {
      this.duration = Integer.parseInt(duration);
    }
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getInterestRate() {
    return String.valueOf(interestRate);
  }

  public void setInterestRate(String interestRate) {
    if (interestRate.isEmpty()) {
      this.interestRate = 0;
    } else {
      this.interestRate = Double.parseDouble(interestRate);
    }
    notifyPropertyChanged(BR.monthlyPayment);
  }

  @Bindable
  public String getAssuranceRate() {
    return String.valueOf(assuranceRate);
  }

  public void setAssuranceRate(String assuranceRate) {
    if (assuranceRate.isEmpty()) {
      this.assuranceRate = 0;
    } else {
      this.assuranceRate = Double.parseDouble(assuranceRate);
    }
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

  @Bindable
  public String getContributionRate() {
    return String.valueOf(contributionRate);
  }

  public void setContributionRate(String contributionRate) {
    if (contributionRate.isEmpty()) {
      this.contributionRate = 0;
    } else {
      this.contributionRate = Double.parseDouble(contributionRate);
    }
    notifyPropertyChanged(BR.contribution);
  }
}
