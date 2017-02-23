package com.gmail.at.kurtiszabi.repositories;

import java.util.List;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;

public interface CarReservationRepository {

  CarReservation save(CarReservation entity);

  List<CarReservation> findByCar(Car car);

}
