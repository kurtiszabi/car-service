package com.gmail.at.kurtiszabi.exceptions;

public class ReservationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ReservationException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return "Reservation failed with the reason being: " + super.getMessage();
  }
}
