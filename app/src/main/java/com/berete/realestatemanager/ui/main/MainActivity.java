package com.berete.realestatemanager.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.detail.PropertyDetailActivity;
import com.berete.realestatemanager.ui.detail.PropertyDetailFragment;
import com.berete.realestatemanager.ui.edit.EditPropertyActivity;
import com.berete.realestatemanager.ui.list.PropertyListAdapter;
import com.berete.realestatemanager.ui.list.PropertyListFragment;
import com.berete.realestatemanager.ui.list.PropertyListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.LAYOUT_ORIENTATION_KEY;
import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.PROPERTY_ID_ARG_KEY;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  PropertyDetailFragment propertyDetailFragment;
  private boolean isLargeScreen;
  private int currentSelectedPropertyID;

  public MainActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_container);
    findViewById(R.id.createProperty)
        .setOnClickListener(__ -> startActivity(new Intent(this, EditPropertyActivity.class)));
    isLargeScreen = findViewById(R.id.detailFragmentContainer) != null;
    setSupportActionBar(findViewById(R.id.toolbar));
    setupViews();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.editProperty) {
      startEditActivity();
      return true;
    } else if (item.getItemId() == R.id.filter) {
      showFilterDialog();
    }
    return super.onOptionsItemSelected(item);
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

  private void startEditActivity() {
    if(currentSelectedPropertyID == 0){
      showSnackBar("No property have been selected yet");
      return;
    }
    final Bundle args = new Bundle();
    args.putInt(PROPERTY_ID_ARG_KEY, currentSelectedPropertyID);
    final Intent editPropertyIntent = new Intent(this, EditPropertyActivity.class);
    editPropertyIntent.putExtras(args);
    startActivity(editPropertyIntent);
  }

  private void showFilterDialog() {}

  private void showSnackBar(String message){
    final View root = findViewById(R.id.root);
    Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
  }
}
