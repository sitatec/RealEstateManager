package com.berete.realestatemanager.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.ui.core.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PropertyListFragment extends Fragment {

  PropertyListViewModel viewModel;

  public static PropertyListFragment newInstance() {
    return new PropertyListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
    return recyclerView;
  }

  private void setupPropertyList(RecyclerView recyclerView) {
    final MainActivity parentActivity = (MainActivity) getActivity();
    final PropertyListAdapter adapter =
        new PropertyListAdapter(new ArrayList<>(), parentActivity::onPropertySelected);
    recyclerView.setAdapter(adapter);
    viewModel.getProperties().observe(getViewLifecycleOwner(), adapter::updateList);
  }
}
