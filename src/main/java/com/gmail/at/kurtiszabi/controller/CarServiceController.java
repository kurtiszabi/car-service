package com.gmail.at.kurtiszabi.controller;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.services.CarService;

@RestController
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
  public Car getCar(@PathParam("id") Long id) {
    return delegate.getCar(id);
  }

  @Override
  @RequestMapping(value = "/reservations", method = RequestMethod.POST)
  public CarReservation reserve(@RequestBody CarReservation reservation) {
    return delegate.reserve(reservation);
  }

}
