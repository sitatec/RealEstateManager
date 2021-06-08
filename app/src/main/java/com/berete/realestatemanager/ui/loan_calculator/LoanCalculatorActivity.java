package com.berete.realestatemanager.ui.loan_calculator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.databinding.ActivityLoanCalculatorBinding;

public class LoanCalculatorActivity extends AppCompatActivity {

  LoanCalculatorViewModel viewModel;
  ActivityLoanCalculatorBinding binding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_loan_calculator);
    setSupportActionBar(findViewById(R.id.toolbar));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(R.string.loan_calculator);
    viewModel = new ViewModelProvider(this).get(LoanCalculatorViewModel.class);
    binding.setBinding(viewModel.getDataBinder());

  }
}
