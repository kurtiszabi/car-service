package com.gmail.at.kurtiszabi.services;

import org.mockito.Mock;

import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;
import com.gmail.at.kurtiszabi.test.AbstractUnitTestBase;

public class CarServiceImplTest extends AbstractUnitTestBase<CarServiceImpl> {

  @Mock
  private CarRepository carRepository;

  @Mock
  private CarReservationRepository carReservationRepository;

  @Override
  public CarServiceImpl createInstance() {
    return new CarServiceImpl(carRepository, carReservationRepository);
  }



}
