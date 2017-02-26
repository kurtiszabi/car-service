package com.gmail.at.kurtiszabi.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class CarserviceApplicationTests {

  private static final Logger LOG = LoggerFactory.getLogger(CarserviceApplicationTests.class);

  @LocalServerPort
  private String port;

  protected RequestSpecification jsonRequest() {
    RestAssured.port = Integer.parseInt(port);
    return RestAssured.given().log().all().accept(ContentType.JSON);
  }

  protected RequestSpecification jsonRequest(Object object) {
    return jsonRequest().contentType(ContentType.JSON).body(object);
  }

  protected Car getCarById(long id) {
    return asCar(jsonRequest().get("/cars/{id}", id));
  }

  protected List<CarReservation> getReservationsByCarId(long id) {
    Response response = jsonRequest().get("/cars/{carId}/reservations", id);
    List<CarReservation> reservations = Arrays.asList(response.as(CarReservation[].class));
    return reservations;
  }

  protected Car asCar(Response response) {
    LOG.debug(response.body().prettyPrint());
    response.then().statusCode(200);
    return response.as(Car.class);
  }

  protected List<Car> asCars(Response response) {
    response.then().statusCode(200);
    LOG.debug(response.body().prettyPrint());
    Car[] cars = response.as(Car[].class);
    return Arrays.asList(cars);
  }

  protected List<Car> getAllCars() {
    Response response = jsonRequest().get("/cars");
    List<Car> cars = asCars(response);
    return cars;
  }

  protected Response trySavingReservation(CarReservation res) {
    Response response = jsonRequest(res).post("cars/{carId}/reservations", res.getCar().getId());
    return response;
  }

  protected <T> T safeGet(Future<T> future) {
    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      throw new IllegalStateException(e);
    }
  }

}
