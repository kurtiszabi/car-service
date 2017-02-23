package com.gmail.at.kurtiszabi.external;

import java.util.Random;

import com.gmail.at.kurtiszabi.domain.CarDetails;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ExternalServiceImpl implements ExternalService {

  private final Multimap<String, String> forbiddenManufacturers = HashMultimap.create();

  private final Random random = new Random(System.currentTimeMillis());

  {
    forbiddenManufacturers.put("US", "Dacia");
    forbiddenManufacturers.put("US", "Volkswagen");
    forbiddenManufacturers.put("Russia", "Ford");
    forbiddenManufacturers.put("Russia", "GM");
  }

  @Override
  public boolean isPermitted(CarDetails details, String country) throws InterruptedException {
    Thread.currentThread().sleep(random.nextInt() % 5000);
    return !forbiddenManufacturers.get(country).contains(details.getManufacturer());
  }

}
