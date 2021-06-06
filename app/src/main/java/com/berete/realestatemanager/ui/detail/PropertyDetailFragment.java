package com.berete.realestatemanager.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.berete.realestatemanager.databinding.PropertyDetailViewBinding;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.list.PhotoListAdapter;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PropertyDetailFragment extends Fragment {

  PropertyDetailViewModel viewModel;
  private int propertyId;
  private int footerLayoutOrientation;
  PropertyDetailViewBinding binding;

  public static PropertyDetailFragment newInstance(Bundle args) {
    PropertyDetailFragment fragment = new PropertyDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Bundle arguments = requireArguments();
    propertyId = arguments.getInt(PropertyDetailActivity.PROPERTY_ID_ARG_KEY);
    footerLayoutOrientation = arguments.getInt(PropertyDetailActivity.LAYOUT_ORIENTATION_KEY);
    viewModel = new ViewModelProvider(this).get(PropertyDetailViewModel.class);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NotNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = PropertyDetailViewBinding.inflate(inflater, container, false);
    binding.setDefaultPointOFInterest(new ArrayList<>());
    binding.setFooterOrientation(footerLayoutOrientation);
    if(propertyId != 0){
      viewModel.getPropertyById(propertyId).observe(getViewLifecycleOwner(), this::setProperty);
    }
    return binding.getRoot();
  }

  public void setProperty(Property property) {
    binding.setProperty(property);
    binding.photoRecyclerView.setAdapter(new PhotoListAdapter(property.getPhotoList()));
    Glide.with(binding.getRoot())
        .load(property.getAgent().getPhotoUrl())
        .centerCrop()
        .into(binding.agentPhoto);
  }

}
