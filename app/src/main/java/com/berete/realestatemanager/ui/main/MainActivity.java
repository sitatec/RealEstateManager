package com.berete.realestatemanager.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.detail.PropertyDetailActivity;
import com.berete.realestatemanager.ui.detail.PropertyDetailFragment;
import com.berete.realestatemanager.ui.edit.EditPropertyActivity;
import com.berete.realestatemanager.ui.list.PropertyListAdapter;
import com.berete.realestatemanager.ui.list.PropertyListFragment;
import com.berete.realestatemanager.ui.list.PropertyListViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.LAYOUT_ORIENTATION_KEY;
import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.PROPERTY_ID_ARG_KEY;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  PropertyListViewModel viewModel;
  PropertyDetailFragment propertyDetailFragment;
  private boolean isLargeScreen;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.createProperty)
        .setOnClickListener(__ -> startActivity(new Intent(this, EditPropertyActivity.class)));
    isLargeScreen = findViewById(R.id.detailFragmentContainer) != null;
    setupViews();
  }

  private void setupViews() {
    setupListFragment();
    if (isLargeScreen) {
      setupDetailFragment();
    }
  }

  private void setupListFragment() {
    final PropertyListFragment propertyListFragment = PropertyListFragment.newInstance();
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.listFragmentContainer, propertyListFragment)
        .commit();
  }

  private void setupDetailFragment() {
    final Bundle args = new Bundle();
    args.putInt(LAYOUT_ORIENTATION_KEY, LinearLayout.HORIZONTAL);
    final PropertyDetailFragment propertyDetailFragment = PropertyDetailFragment.newInstance(args);
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.detailFragmentContainer, propertyDetailFragment)
        .commit();
  }

  public void onPropertySelected(Property property) {
    if (isLargeScreen) {
      propertyDetailFragment.setProperty(property);
    } else {
      startDetailActivity(property.getId());
    }
  }

  private void startDetailActivity(int propertyId) {
    final Bundle bundle = new Bundle();
    bundle.putInt(PROPERTY_ID_ARG_KEY, propertyId);
    final Intent startDetailIntent = new Intent(this, PropertyDetailActivity.class);
    startDetailIntent.putExtras(bundle);
    startActivity(startDetailIntent);
  }
}
