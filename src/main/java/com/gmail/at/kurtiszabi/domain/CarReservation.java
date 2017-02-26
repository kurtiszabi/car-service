package com.gmail.at.kurtiszabi.domain;

import java.time.LocalDateTime;

public class CarReservation {

  private Long id;

  private LocalDateTime from;

  private LocalDateTime to;

  private String user;
  
  private Car car;

  private String country;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getFrom() {
    return from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return to;
  }

  public void setTo(LocalDateTime to) {
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "CarReservation [id=" + id + ", from=" + from + ", to=" + to + ", user=" + user
        + ", country=" + country + ", car=" + (car != null ? car.getId() : "null") + "]";
  }
}
