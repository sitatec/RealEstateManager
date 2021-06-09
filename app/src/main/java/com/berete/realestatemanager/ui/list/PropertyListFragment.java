package com.berete.realestatemanager.ui.list;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.core.MainActivity;
import com.berete.realestatemanager.ui.core.property_filter.PropertyFilterDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PropertyListFragment extends Fragment {

  PropertyListViewModel viewModel;
  PropertyFilterDialog propertyFilterDialog;
  PropertyListAdapter adapter;
  List<Property> propertyList;
  List<Property.PointOfInterest> pointOfInterestList;

  public static PropertyListFragment newInstance() {
    return new PropertyListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.filter) {
      if (propertyFilterDialog == null) {
        propertyFilterDialog =
            new PropertyFilterDialog(propertyList, pointOfInterestList, adapter::updateList);
      }
      if (!propertyFilterDialog.isAdded()) {
        propertyFilterDialog.show(getParentFragmentManager(), "filter_dialog");
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NotNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final RecyclerView recyclerView =
        (RecyclerView) inflater.inflate(R.layout.property_list, container, false);
    viewModel = new ViewModelProvider(this).get(PropertyListViewModel.class);
    setupPropertyList(recyclerView);
    viewModel
        .getAllPointOfInterest()
        .observe(
            getViewLifecycleOwner(), pointOfInterests -> pointOfInterestList = pointOfInterests);
    return recyclerView;
  }

  private void setupPropertyList(RecyclerView recyclerView) {
    final MainActivity parentActivity = (MainActivity) getActivity();
    adapter = new PropertyListAdapter(new ArrayList<>(), parentActivity::onPropertySelected);
    recyclerView.setAdapter(adapter);
    viewModel
        .getProperties()
        .observe(
            getViewLifecycleOwner(),
            properties -> {
              propertyList = properties;
              adapter.updateList(properties);
            });
  }

  @Override
  public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // TODO Refactoring (use bundle args for the dialog dependencies instead of dismissing it on
    //  configuration change)
    if (propertyFilterDialog.isAdded()) {
      propertyFilterDialog.dismiss();
    }
  }

}
