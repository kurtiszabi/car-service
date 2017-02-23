package com.gmail.at.kurtiszabi.domain;

import java.time.LocalDate;

public class CarDetails {

  private String manufacturer;

  private String model;
  
  private LocalDate manufacuted;
  
  private String color;

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public LocalDate getManufacuted() {
    return manufacuted;
  }

  public void setManufacuted(LocalDate manufacuted) {
    this.manufacuted = manufacuted;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "CarDetails [manufacturer=" + manufacturer + ", model=" + model + ", manufacuted="
        + manufacuted + "]";
  }

}
