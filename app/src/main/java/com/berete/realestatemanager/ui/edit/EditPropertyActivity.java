package com.berete.realestatemanager.ui.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.berete.realestatemanager.databinding.ActivityEditPropertyBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditPropertyActivity extends AppCompatActivity {

  public static final String PROPERTY_ID_KEY = "PROPERTY_ID_KEY";

  private ActivityEditPropertyBinding binding;
  private EditPropertyViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityEditPropertyBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    viewModel = new  ViewModelProvider(this).get(EditPropertyViewModel.class);
    bindProperty();
  }

  private void bindProperty(){
    final int propertyId = getIntent().getIntExtra(PROPERTY_ID_KEY, 0);
    if(propertyId != 0) {
      viewModel.updateProperty(propertyId).observe(this, binding::setProperty);
    }else {
      binding.setProperty(viewModel.createNewProperty());
    }
  }
}