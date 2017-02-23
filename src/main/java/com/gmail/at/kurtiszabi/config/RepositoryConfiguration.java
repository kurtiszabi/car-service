package com.gmail.at.kurtiszabi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarRepositoryImpl;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepositoryImpl;

@Configuration
public class RepositoryConfiguration {

  @Bean
  public CarRepository carRepository() {
    return new CarRepositoryImpl();
  }

  @Bean
  public CarReservationRepository carReservationRepository() {
    return new CarReservationRepositoryImpl();
  }

}
