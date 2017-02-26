package com.gmail.at.kurtiszabi.services;

import java.util.List;
import java.util.function.Predicate;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.exceptions.NotFoundException;
import com.gmail.at.kurtiszabi.external.ExternalService;
import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;

public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  private final CarReservationRepository carReservationRepository;

  private final ExternalService externalService;

  public CarServiceImpl(CarRepository carRepository,
      CarReservationRepository carReservationRepository, ExternalService externalService) {
    super();
    this.carRepository = carRepository;
    this.carReservationRepository = carReservationRepository;
    this.externalService = externalService;
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
    checkPermissonForTargetCountry(reservation, car);
    synchronized (car) {
      checkInterleavingReservations(reservation, car);
      return carReservationRepository.save(reservation);
    }
  }

  private void checkPermissonForTargetCountry(CarReservation reservation, Car car) {
    boolean isPermitted = true;
    if (!"HU".equalsIgnoreCase(reservation.getCountry())) {
      isPermitted = externalService.isPermitted(car.getDetails(), reservation.getCountry());
    }
    if (!isPermitted) {
      throw new IllegalArgumentException(
          "Reservation failed with the reason being: selected car is not permitted in the target country");
    }
  }

  private void checkInterleavingReservations(CarReservation reservation, Car car) {
    Predicate<CarReservation> predicate = (r) -> r.getCar().equals(car)
        && (!r.getTo().isBefore(reservation.getFrom())
            && !r.getFrom().isAfter(reservation.getTo()));
    boolean exists = carReservationRepository.exists(predicate);
    if (exists) {
      throw new IllegalArgumentException(
          "Reservation failed with the reason being: already booked");
    }
  }

  @Override
  public List<CarReservation> getReservationsByCar(long carId) {
    Car car = getCar(carId);
    return carReservationRepository.findByCar(car);
  }

}
