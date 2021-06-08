package com.berete.realestatemanager.data.sources.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.berete.realestatemanager.data.sources.local.entities.Relationships;
import com.berete.realestatemanager.data.sources.local.entities.RealEstateAgentEntity;

import java.util.List;

@Dao
public interface RealEstateAgentDao {

  @Query("SELECT * FROM real_estate_agent WHERE agent_id=:id")
  RealEstateAgentEntity getById(int id);

  @Update
  void update(RealEstateAgentEntity agent);

  @Delete
  void delete(RealEstateAgentEntity agent);

  @Insert
  void create(RealEstateAgentEntity... agent);

  @Query("SELECT * FROM real_estate_agent")
  LiveData<List<RealEstateAgentEntity>> getAll();

  @Transaction
  @Query("SELECT * FROM real_estate_agent")
  List<Relationships.RealEstateAgentWithProperties> getAllAgentWithProperties();
}
