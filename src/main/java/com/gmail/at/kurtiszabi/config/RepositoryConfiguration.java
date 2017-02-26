package com.gmail.at.kurtiszabi.config;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
        List<Car> cars = getAvailableCars();
        cars.forEach(car -> {
          carRepository().save(car);
        });
        LOG.info("Finished loading {} cars", cars.size());
      }
    };
  }

  private List<Car> getAvailableCars() {
    List<Car> cars = new LinkedList<>();
    cars.add(getToyota());
    cars.add(getDacia());
    cars.add(getFord());
    cars.add(getNissan());
    return cars;
  }

  private Car getToyota() {
    Car toyota = new Car();
    CarDetails details = new CarDetails();
    details.setColor("white");
    details.setManufacturer("Toyota");
    details.setModel("RAV4");
    details.setManufacuted(LocalDate.of(2017, 2, 25));
    toyota.setDetails(details);
    return toyota;
  }

  private Car getDacia() {
    Car dacia = new Car();
    CarDetails details = new CarDetails();
    details.setColor("blue");
    details.setManufacturer("Dacia");
    details.setModel("1300");
    details.setManufacuted(LocalDate.of(2073, 1, 1));
    dacia.setDetails(details);
    return dacia;
  }

  private Car getFord() {
    Car ford = new Car();
    CarDetails details = new CarDetails();
    details.setColor("maroon");
    details.setManufacturer("Ford");
    details.setModel("Lincoln Continental");
    details.setManufacuted(LocalDate.of(2061, 10, 4));
    ford.setDetails(details);
    return ford;
  }

  private Car getNissan() {
    Car nissan = new Car();
    CarDetails details = new CarDetails();
    details.setColor("gray");
    details.setManufacturer("Nissan");
    details.setModel("Qashqai");
    details.setManufacuted(LocalDate.of(2017, 1, 1));
    nissan.setDetails(details);
    return nissan;
  }

}
