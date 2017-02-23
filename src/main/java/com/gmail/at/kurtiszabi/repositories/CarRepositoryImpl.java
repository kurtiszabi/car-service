package com.gmail.at.kurtiszabi.repositories;

import java.util.ArrayList;
import java.util.List;

import com.gmail.at.kurtiszabi.domain.Car;

public class CarRepositoryImpl extends Repository<Car> implements CarRepository {

  @Override
  public Car save(Car entity) {
    if (entity.getId() == null) {
      entity.setId(id.incrementAndGet());
    }
    store.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public Car findById(Long id) {
    return store.get(id);
  }

  @Override
  public List<Car> findAll() {
    return new ArrayList<>(store.values());
  }

}
