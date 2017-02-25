package com.gmail.at.kurtiszabi.repositories;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;

public class CarReservationRepositoryImpl extends Repository<CarReservation>
    implements CarReservationRepository {

  @Override
  public CarReservation save(CarReservation entity) {
    if (entity.getId() == null) {
      entity.setId(id.incrementAndGet());
    }
    store.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public List<CarReservation> findByCar(Car car) {
    return store.values().stream().filter(c -> Objects.equals(c.getCar(), car)).collect(toList());
  }

  @Override
  public boolean exists(Predicate<CarReservation> predicate) {
    return store.values().parallelStream().anyMatch(predicate);
  }

}
