package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
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
    CarReservation saved = saveReservation(createDefaultReservation(car));
    assertThat(saved.getId(), greaterThan(0L));
  }

  @Test
  public void testSavingReservationForANonExistingCar() {
    Car nonExistingCar = new Car();
    nonExistingCar.setId(0L);
    CarReservation res = createDefaultReservation(nonExistingCar);
    Response response = trySavingReservation(res);
    response.then().statusCode(400);
    assertThat(response.body().jsonPath().getString("message"),
        allOf(containsString("Reservation failed"), containsString("No such car")));
  }

  private CarReservation createDefaultReservation(Car car) {
    CarReservation res = new CarReservation();
    res.setCar(car);
    res.setUser("Szabolcs Kurti");
    LocalDateTime now = LocalDateTime.now();
    res.setFrom(now.plusDays(1));
    res.setTo(now.plusDays(2));
    return res;
  }

  private Response trySavingReservation(CarReservation res) {
    Response response = jsonRequest(res).post("cars/{carId}/reservations", res.getCar().getId());
    return response;
  }

  private CarReservation saveReservation(CarReservation res) {
    Response response = trySavingReservation(res);
    response.then().statusCode(200);
    return response.as(CarReservation.class);
  }

}
