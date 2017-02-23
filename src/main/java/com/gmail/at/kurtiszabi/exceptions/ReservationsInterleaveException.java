package com.gmail.at.kurtiszabi.exceptions;

public class ReservationsInterleaveException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ReservationsInterleaveException(String msg) {
    super(msg);
  }

}
