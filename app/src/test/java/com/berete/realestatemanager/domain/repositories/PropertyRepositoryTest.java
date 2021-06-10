package com.berete.realestatemanager.domain.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.berete.realestatemanager.domain.data_providers.PropertyProvider;
import com.berete.realestatemanager.test_utils.LiveDataUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Collections;

import static com.berete.realestatemanager.test_utils.FakeData.fakeProperty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertyRepositoryTest {

  private PropertyProvider propertyProvider;
  private PropertyRepository propertyRepository;

  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  @Before
  public void setUp() {
    propertyProvider = mock(PropertyProvider.class);
    propertyRepository = new PropertyRepository(propertyProvider);
    setUpMocks();
  }

  private void setUpMocks(){
    when(propertyProvider.getById(fakeProperty.getId())).thenReturn(new MutableLiveData<>(fakeProperty));
    when(propertyProvider.getAll()).thenReturn(new MutableLiveData<>(Collections.singletonList(fakeProperty)));
  }

  @Test
  public void create() {
    propertyRepository.create(fakeProperty);
    verify(propertyProvider, timeout(100)).create(fakeProperty);
  }

  @Test
  public void getById() throws InterruptedException {
    assertEquals(LiveDataUtils.getLiveDataValue(propertyRepository.getById(fakeProperty.getId())), fakeProperty);
  }

  @Test
  public void getAll() throws InterruptedException {
    assertTrue(LiveDataUtils.getLiveDataValue(propertyRepository.getAll()).contains(fakeProperty));
  }

  @Test
  public void update() {
    propertyRepository.update(fakeProperty);
    verify(propertyProvider, timeout(100)).update(fakeProperty);
  }

  @Test
  public void delete() {
    propertyRepository.delete(fakeProperty);
    verify(propertyProvider, timeout(100)).delete(fakeProperty);
  }
}
