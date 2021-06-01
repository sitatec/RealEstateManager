package com.berete.realestatemanager.data_sources.local.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.RealEstateAgent;


@Entity(tableName = "real_estate_agent")
public class RealEstateAgentEntity extends RealEstateAgent {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public RealEstateAgentEntity(String name, String photoUrl) {
    super(name, photoUrl);
  }
}
