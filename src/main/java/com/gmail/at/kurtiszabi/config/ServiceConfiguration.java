package com.gmail.at.kurtiszabi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;
import com.gmail.at.kurtiszabi.services.CarService;
import com.gmail.at.kurtiszabi.services.CarServiceImpl;

@Configuration
public class ServiceConfiguration {

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private CarReservationRepository carReservationRepository;

  @Bean
  public CarService carServiceImpl() {
    return new CarServiceImpl(carRepository, carReservationRepository);
  }

}
