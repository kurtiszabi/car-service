package com.gmail.at.kurtiszabi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.services.CarService;

@RestController
@RequestMapping(produces = "application/json")
public class CarServiceController implements CarService {

  @Autowired
  @Qualifier("carServiceImpl")
  private CarService delegate;

  @Override
  @RequestMapping(value = "/cars", method = RequestMethod.GET)
  public List<Car> getCars() {
    return delegate.getCars();
  }

  @Override
  @RequestMapping(value = "/cars/{id}", method = RequestMethod.GET)
  public Car getCar(@PathVariable("id") long id) {
    return delegate.getCar(id);
  }

  @Override
  @RequestMapping(value = "/cars/{id}/reservations", method = RequestMethod.POST,
      produces = "application/json")
  public CarReservation makeAReservation(@RequestBody CarReservation reservation) {
    return delegate.makeAReservation(reservation);
  }

  @Override
  @RequestMapping(value = "/cars/{id}/reservations", method = RequestMethod.GET,
      produces = "application/json")
  public List<CarReservation> getReservationsByCar(@PathVariable("id") long id) {
    return delegate.getReservationsByCar(id);
  }
}
