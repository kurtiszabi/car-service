package com.gmail.at.kurtiszabi.services;

import java.util.List;
import java.util.function.Predicate;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.exceptions.NotFoundException;
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
    return carRepository.findAll();
  }

  @Override
  public Car getCar(long id) {
    Car car = carRepository.findById(id);
    if (car == null) {
      throw new NotFoundException("No such car (id=" + id + ")");
    }
    return car;
  }

  @Override
  public CarReservation makeAReservation(CarReservation reservation) {
    Long id = reservation.getCar() != null ? reservation.getCar().getId() : 0L;
    Car car = carRepository.findById(id);
    if (car == null) {
      throw new IllegalArgumentException(
          "Reservation failed with the reason being: no such car (id=" + id + ")");
    }
    Predicate<CarReservation> predicate = (r) -> r.getCar().equals(car)
        && (!r.getTo().isBefore(reservation.getFrom())
            && !r.getFrom().isAfter(reservation.getTo()));

    ;
    boolean exists = carReservationRepository.exists(predicate);
    if (exists) {
      throw new IllegalArgumentException(
          "Reservation failed with the reason being: already booked");
    }


    return carReservationRepository.save(reservation);
  }

  @Override
  public List<CarReservation> getReservationsByCar(long carId) {
    Car car = getCar(carId);
    return carReservationRepository.findByCar(car);
  }

}
