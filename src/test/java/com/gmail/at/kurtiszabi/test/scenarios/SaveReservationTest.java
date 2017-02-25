package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.time.LocalDateTime;

import org.junit.Test;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class SaveReservationTest extends CarserviceApplicationTests {

  private static final long KNOWN_CAR_ID = 1L;

  @Test
  public void testSavingAReservation() {
    Car car = getCarById(KNOWN_CAR_ID);
    CarReservation res = new CarReservation();
    res.setCar(car);
    res.setUser("Szabolcs Kurti");
    LocalDateTime now = LocalDateTime.now();
    res.setFrom(now.plusDays(1));
    res.setTo(now.plusDays(2));
    CarReservation saved = saveReservation(res);
    assertThat(saved.getId(), greaterThan(0L));
  }

  private CarReservation saveReservation(CarReservation res) {
    Response response = jsonRequest(res).post("cars/{carId}/reservations", res.getCar().getId());
    response.then().statusCode(200);
    return response.as(CarReservation.class);
  }

}
