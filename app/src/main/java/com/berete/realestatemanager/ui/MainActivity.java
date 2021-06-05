package com.berete.realestatemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.ui.edit.EditPropertyActivity;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  MainActivityViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.createProperty)
        .setOnClickListener(__ -> startActivity(new Intent(this, EditPropertyActivity.class)));

    viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

    setupPropertyList();
  }

  private void setupPropertyList() {
    final RecyclerView recyclerView = findViewById(R.id.recyclerView);
    final PropertyListAdapter adapter = new PropertyListAdapter(new ArrayList<>());
    recyclerView.setAdapter(adapter);
    viewModel.getProperties().observe(this, adapter::updateList);
  }
}
