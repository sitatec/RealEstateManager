package com.berete.realestatemanager.domain.repositories;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.data_providers.RealEstateAgentProvider;
import com.berete.realestatemanager.domain.models.RealEstateAgent;

import java.util.List;

import javax.inject.Inject;

public class AgentRepository {

  private final RealEstateAgentProvider realEstateAgentProvider;

  @Inject
  public AgentRepository(RealEstateAgentProvider realEstateAgentProvider) {
    this.realEstateAgentProvider = realEstateAgentProvider;
  }

  public LiveData<List<RealEstateAgent>> getAll(){
    return realEstateAgentProvider.getAll();
  }

}
