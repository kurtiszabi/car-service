package com.gmail.at.kurtiszabi.repositories;

import java.util.List;

import com.gmail.at.kurtiszabi.domain.Car;

public interface CarRepository {

  Car save(Car entity);

  Car findById(Long id);

  List<Car> findAll();

}
