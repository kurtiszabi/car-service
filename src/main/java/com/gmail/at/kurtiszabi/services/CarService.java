package com.gmail.at.kurtiszabi.services;

import java.util.List;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;

public interface CarService {

  List<Car> getCars();

  Car getCar(Long id);

  CarReservation reserve(CarReservation reservation);

}
