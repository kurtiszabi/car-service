package com.gmail.at.kurtiszabi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.services.CarService;

public class CarServiceController implements CarService {

  @Autowired
  @Qualifier("carServiceImpl")
  private CarService delegate;

  @Override
  public List<Car> getCars() {
    return delegate.getCars();
  }

  @Override
  public Car getCar(Long id) {
    return delegate.getCar(id);
  }

  @Override
  public CarReservation reserve(CarReservation reservation) {
    return delegate.reserve(reservation);
  }

}
