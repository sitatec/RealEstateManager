package com.berete.realestatemanager.data.sources.local.adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.berete.realestatemanager.data.sources.local.dao.RealEstateAgentDao;
import com.berete.realestatemanager.data.sources.local.entities.RealEstateAgentEntity;
import com.berete.realestatemanager.domain.data_providers.RealEstateAgentProvider;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import java.util.List;
import java.util.stream.Collectors;

public class RealEstateAgentDataProvider implements RealEstateAgentProvider {

  private final RealEstateAgentDao realEstateAgentDao;

  public RealEstateAgentDataProvider(RealEstateAgentDao realEstateAgentDao) {
    this.realEstateAgentDao = realEstateAgentDao;
  }

  @Override
  public LiveData<List<RealEstateAgent>> getAll() {
    return Transformations.map(realEstateAgentDao.getAll(),this::toModels );
  }

  private List<RealEstateAgent> toModels(List<RealEstateAgentEntity> entities){
    return entities.stream().map(RealEstateAgentEntity::toModel).collect(Collectors.toList());
  }
}
