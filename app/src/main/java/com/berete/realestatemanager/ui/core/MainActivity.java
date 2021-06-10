package com.berete.realestatemanager.ui.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Property;
import com.berete.realestatemanager.ui.detail.PropertyDetailActivity;
import com.berete.realestatemanager.ui.detail.PropertyDetailFragment;
import com.berete.realestatemanager.ui.edit.EditPropertyActivity;
import com.berete.realestatemanager.ui.list.PropertyListFragment;
import com.berete.realestatemanager.ui.loan_calculator.LoanCalculatorActivity;
import com.berete.realestatemanager.ui.map.MapActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.LAYOUT_ORIENTATION_KEY;
import static com.berete.realestatemanager.ui.detail.PropertyDetailActivity.PROPERTY_ID_ARG_KEY;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  PropertyDetailFragment propertyDetailFragment;
  private boolean isLargeScreen;
  private int currentSelectedPropertyID;
  ActionBarDrawerToggle actionBarDrawerToggle;
  Toolbar toolbar;

  public MainActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_container);
    findViewById(R.id.createProperty)
        .setOnClickListener(__ -> startActivity(new Intent(this, EditPropertyActivity.class)));
    isLargeScreen = findViewById(R.id.detailFragmentContainer) != null;
    Log.d("MAIN_ACTIVITY", "__ isLargeScreen = " + isLargeScreen);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setupViews();
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    actionBarDrawerToggle.syncState();
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
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupViews() {
    setupListFragment();
    if (isLargeScreen) {
      setupDetailFragment();
    }
    setupNavigationDrawer();
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
    propertyDetailFragment = PropertyDetailFragment.newInstance(args);
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.detailFragmentContainer, propertyDetailFragment)
        .commit();
  }

  private void setupNavigationDrawer(){
    final DrawerLayout drawerLayout = findViewById(R.id.root);
    actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(actionBarDrawerToggle);
    final NavigationView navigationView = findViewById(R.id.navigationView);
    navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
  }

  private boolean onNavigationItemSelected(MenuItem menuItem){
    if (menuItem.getItemId() == R.id.map){
      startActivity(new Intent(this, MapActivity.class));
      return true;
    } else if(menuItem.getItemId() == R.id.loanCalculator){
      startActivity(new Intent(this, LoanCalculatorActivity.class));
    }
    return false;
  }

  public void onPropertySelected(Property property) {
    if (isLargeScreen) {
      propertyDetailFragment.setProperty(property);
      currentSelectedPropertyID = property.getId();
    } else {
      startDetailActivity(property.getId());
    }
  }

  private void startDetailActivity(int propertyId) {
    final Bundle arguments = new Bundle();
    arguments.putInt(PROPERTY_ID_ARG_KEY, propertyId);
    arguments.putInt(LAYOUT_ORIENTATION_KEY, LinearLayout.VERTICAL);
    final Intent startDetailIntent = new Intent(this, PropertyDetailActivity.class);
    startDetailIntent.putExtras(arguments);
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

  private void showSnackBar(String message){
    final View root = findViewById(R.id.root);
    Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
  }
}
