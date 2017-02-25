package com.gmail.at.kurtiszabi.controller.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gmail.at.kurtiszabi.exceptions.NotFoundException;
import com.google.common.collect.ImmutableMap;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private static class Fault {

    private final String message;

    public Fault(String message) {
      super();
      this.message = message;
    }
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleResourceMissingException(Exception ex, WebRequest request) {
    return new ResponseEntity<Object>(ImmutableMap.of("message", ex.getMessage()),
        new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

}