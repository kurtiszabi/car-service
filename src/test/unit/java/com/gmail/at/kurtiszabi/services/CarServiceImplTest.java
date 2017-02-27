package com.gmail.at.kurtiszabi.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.exceptions.NotFoundException;
import com.gmail.at.kurtiszabi.external.ExternalService;
import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;
import com.gmail.at.kurtiszabi.test.AbstractUnitTestBase;
import com.google.common.collect.Lists;

public class CarServiceImplTest extends AbstractUnitTestBase<CarServiceImpl> {

  @Mock
  private CarRepository carRepository;

  @Mock
  private CarReservationRepository carReservationRepository;

  @Mock
  private ExternalService extenalService;

  @Mock
  private Car car;

  private List<Car> cars = Lists.newArrayList(car);

  @Override
  public CarServiceImpl createInstance() {
    return new CarServiceImpl(carRepository, carReservationRepository, extenalService);
  }

  @Test
  public void testGettingAllCars() {
    when(carRepository.findAll()).thenReturn(cars);
    List<Car> list = classUnderTest.getCars();
    assertThat(list, equalTo(this.cars));
  }

  @Test
  public void testGettingASingleCar() {
    when(carRepository.findById(Mockito.anyLong())).thenReturn(car);
    Car single = classUnderTest.getCar(1L);
    assertThat(single, equalTo(car));
  }

  @Test(expected = NotFoundException.class)
  public void testGettingANonExistentCar() {
    classUnderTest.getCar(1L);
  }

  @Test
  @Ignore("Until implemented")
  public void testReservation() {
    // TODO implement test case
  }
}
