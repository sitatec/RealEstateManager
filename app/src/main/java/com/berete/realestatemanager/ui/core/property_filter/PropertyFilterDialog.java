package com.berete.realestatemanager.ui.core.property_filter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.databinding.DialogPropertyFilterBinding;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.custom.PointOfInterestView;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyFilterDialog extends DialogFragment {

  final List<Property> propertyList;
  final List<Property.PointOfInterest> pointOfInterestList;
  Set<Property.PointOfInterest> pointOfInterestSet;
  final Consumer<List<Property>> onListFiltered;
  DialogPropertyFilterBinding binding;

  final FilterData filterData = new FilterData();
  final PropertyFilter propertyFilter = new PropertyFilter();

  public PropertyFilterDialog(
      List<Property> propertyList,
      List<Property.PointOfInterest> pointOfInterestList,
      Consumer<List<Property>> onListFiltered) {
    this.propertyList = propertyList;
    this.pointOfInterestList = pointOfInterestList;
    this.onListFiltered = onListFiltered;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    binding = DialogPropertyFilterBinding.inflate(getLayoutInflater());
    pointOfInterestSet = new HashSet<>();
    setupPointOfInterestsView();
    return new AlertDialog.Builder(getContext())
        .setTitle(getString(R.string.property_filter_dialog_title))
        .setView(binding.getRoot())
        .setPositiveButton(getString(R.string.apply), this::applyFilter)
        .setNegativeButton(
            getString(R.string.reset),
            (dialog, which) -> {
              onListFiltered.accept(propertyList);
              dialog.dismiss();
            })
        .create();
  }

  private void setupPointOfInterestsView() {
    PointOfInterestView pointOfInterestView;
    for (final Property.PointOfInterest pointOfInterest : pointOfInterestList) {
      pointOfInterestView = new PointOfInterestView(getContext());
      pointOfInterestView.setOnClickListener(this::togglePointOfInterestSelection);
      pointOfInterestView.setText(pointOfInterest.getName());
      pointOfInterestView.setTag(pointOfInterest);
      binding.pointOfInterestsContainer.addView(pointOfInterestView);
    }
  }

  private void togglePointOfInterestSelection(View pointOfInterestView) {
    ((PointOfInterestView) pointOfInterestView).toggleChecked();
    final Property.PointOfInterest pointOfInterest =
        (Property.PointOfInterest) pointOfInterestView.getTag();
    if (!pointOfInterestSet.add(pointOfInterest)) {
      pointOfInterestSet.remove(pointOfInterest);
    }
  }

  private void applyFilter(DialogInterface dialog, int which) {
    if (isSurfaceFieldsValid() && isPriceFieldsValid()) {
      Log.d("SHOULDN'T", "BE SHOWN");
      setFilterData();
      final List<Property> filteredList =
          propertyFilter
              .initFilter(filterData)
              .filterByMinMaxSurface()
              .filterByMinMaxPrice()
              .filterByPointOfInterest()
              .filterByPublishedLessThanXWeeks()
              .filterBySoldLastXMonths()
              .filterByLocality()
              .filterByMinPhotos()
              .apply();
      onListFiltered.accept(filteredList);
      dialog.dismiss();
    }
  }

  private void setFilterData() {
    filterData.setMaxSurface(getFieldValueAsDouble(binding.maxSurfaceEdit));
    filterData.setMinSurface(getFieldValueAsDouble(binding.minSurfaceEdit));
    filterData.setMaxPrice(getFieldValueAsDouble(binding.maxPriceEdit));
    filterData.setMinPrice(getFieldValueAsDouble(binding.minPriceEdit));
    filterData.setNumberOfWeeks(getFieldValueAsInt(binding.lessThanXWeeksEdit));
    filterData.setNumberOfMonths(getFieldValueAsInt(binding.lastXMonthsEdit));
    filterData.setMinPhotos(getFieldValueAsInt(binding.minPhotoCountEdit));
    filterData.setLocality(binding.localityEdit.getText().toString());
    filterData.setPointOfInterestSet(pointOfInterestSet);
    filterData.setPropertyList(propertyList);
  }

  private boolean isSurfaceFieldsValid() {
    final double maxSurface = getFieldValueAsDouble(binding.maxSurfaceEdit);
    if (maxSurface != 0 && maxSurface < getFieldValueAsDouble(binding.minSurfaceEdit)) {
      binding.minPrice.setError("Min price greater than max price");
      return false;
    }
    return true;
  }

  private boolean isPriceFieldsValid() {
    final double maxPrice = getFieldValueAsDouble(binding.maxPriceEdit);
    if (maxPrice != 0 && maxPrice < getFieldValueAsDouble(binding.minPriceEdit)) {
      binding.minPrice.setError("Min price greater than max price");
      return false;
    }
    return true;
  }

  private double getFieldValueAsDouble(TextInputEditText field) {
    final Editable text = field.getText();
    if (text == null || text.toString().isEmpty()) {
      return 0;
    }
    return Double.parseDouble(text.toString());
  }

  private int getFieldValueAsInt(TextInputEditText field) {
    final Editable text = field.getText();
    if (text == null || text.toString().isEmpty()) {
      return 0;
    }
    return Integer.parseInt(text.toString());
  }
}
