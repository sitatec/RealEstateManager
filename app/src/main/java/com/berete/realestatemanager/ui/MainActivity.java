package com.berete.realestatemanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.ui.edit.EditPropertyActivity;

public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.createProperty).setOnClickListener(__ -> startActivity(new Intent(this, EditPropertyActivity.class)));

  }
}