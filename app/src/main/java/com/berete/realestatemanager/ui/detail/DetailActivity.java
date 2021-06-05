package com.berete.realestatemanager.ui.detail;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.databinding.PropertyDetailBinding;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.PhotoListAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailActivity extends AppCompatActivity {

  public static final String PROPERTY_ID_ARG_KEY = "PROPERTY_ID_ARG_KEY";

  PropertyDetailBinding binding;
  PropertyDetailViewModel viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.property_detail);
    viewModel = new ViewModelProvider(this).get(PropertyDetailViewModel.class);
    binding.setFooterOrientation(LinearLayout.VERTICAL);
    binding.setDefaultPointOFInterest(new ArrayList<>());
    viewModel.getPropertyById(getPropertyId()).observe(this, this::onPropertyFetched);
  }

  private int getPropertyId() {
    return getIntent().getIntExtra(PROPERTY_ID_ARG_KEY, 1);
  }

  private void onPropertyFetched(Property property) {
    binding.setProperty(property);
    binding.photoRecyclerView.setAdapter(new PhotoListAdapter(property.getPhotoList()));
    Glide.with(binding.getRoot())
        .load(property.getAgent().getPhotoUrl())
        .centerCrop()
        .into(binding.agentPhoto);
  }
}
