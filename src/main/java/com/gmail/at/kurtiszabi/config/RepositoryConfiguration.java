package com.gmail.at.kurtiszabi.config;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarDetails;
import com.gmail.at.kurtiszabi.repositories.CarRepository;
import com.gmail.at.kurtiszabi.repositories.CarRepositoryImpl;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepository;
import com.gmail.at.kurtiszabi.repositories.CarReservationRepositoryImpl;

@Configuration
public class RepositoryConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(RepositoryConfiguration.class);

  @Bean
  public CarRepository carRepository() {
    return new CarRepositoryImpl();
  }

  @Bean
  public CarReservationRepository carReservationRepository() {
    return new CarReservationRepositoryImpl();
  }

  @Bean
  public InitializingBean loadData() {
    return new InitializingBean() {
      @Override
      public void afterPropertiesSet() throws Exception {
        LOG.info("Start loading available cars");
        Car toyota = new Car();
        CarDetails details = new CarDetails();
        details.setColor("white");
        details.setManufacturer("Toyota");
        details.setModel("RAV4");
        details.setManufacuted(LocalDate.of(2017, 2, 25));
        toyota.setDetails(details);
        carRepository().save(toyota);
      }
    };
  }

}
