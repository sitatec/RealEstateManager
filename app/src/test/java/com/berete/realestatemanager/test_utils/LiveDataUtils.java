package com.berete.realestatemanager.test_utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;

public class LiveDataUtils {
  public static <T> T getLiveDataValue(LiveData<T> liveData) throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    final Object[] result = new Object[1];
    liveData.observeForever(new Observer<T>() {
      int i = 0;
      @Override
      public void onChanged(T value) {
        result[0] = value;
        countDownLatch.countDown();
        liveData.removeObserver(this);
      }
    });
    if(countDownLatch.getCount() > 0) countDownLatch.wait();
    return (T) result[0];
  }
}