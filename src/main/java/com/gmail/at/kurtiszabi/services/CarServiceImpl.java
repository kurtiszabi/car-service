package com.gmail.at.kurtiszabi.services;

import java.util.List;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;

public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  private final CarReservationRepository carReservationRepository;

  public CarServiceImpl(CarRepository carRepository,
      CarReservationRepository carReservationRepository) {
    super();
    this.carRepository = carRepository;
    this.carReservationRepository = carReservationRepository;
  }

  @Override
  public List<Car> getCars() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Car getCar(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CarReservation reserve(CarReservation reservation) {
    // TODO Auto-generated method stub
    return null;
  }

}
