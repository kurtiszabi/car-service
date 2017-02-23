package com.gmail.at.kurtiszabi.domain;

import java.time.LocalDate;

public class CarReservation {

  private Long id;

  private LocalDate from;

  private LocalDate to;

  private String user;
  
  private Car car;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getFrom() {
    return from;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  public LocalDate getTo() {
    return to;
  }

  public void setTo(LocalDate to) {
    this.to = to;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  @Override
  public String toString() {
    return "CarReservation [id=" + id + ", from=" + from + ", to=" + to + ", user=" + user
        + ", car=" + (car != null ? car.getId() : "null") + "]";
  }



}
