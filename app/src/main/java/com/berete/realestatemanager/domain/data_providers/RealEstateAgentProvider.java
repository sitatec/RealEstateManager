package com.berete.realestatemanager.domain.data_providers;

import androidx.lifecycle.LiveData;

import com.berete.realestatemanager.domain.models.RealEstateAgent;

import java.util.List;

public interface RealEstateAgentProvider {
  LiveData<List<RealEstateAgent>> getAll();
}
