package com.berete.realestatemanager.data_sources.local.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.berete.realestatemanager.domain.models.RealEstateAgent;


@Entity(tableName = "real_estate_agent")
public class RealEstateAgentEntity extends RealEstateAgent {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "agent_id")
  public int id;

//  public RealEstateAgentEntity(RealEstateAgent parent){
//    super(parent.getName(), parent.getPhotoUrl());
//    if(parent.getId() != 0){
//      id = parent.getId();
//    }
//  }

  public RealEstateAgent toModel(){
    final RealEstateAgent model = new RealEstateAgent(getName(), getPhotoUrl());
    model.setId(id);
    return model;
  }

  public RealEstateAgentEntity(String name, String photoUrl) {
    super(name, photoUrl);
  }
}
