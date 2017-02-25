package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class GetAllCarsTest extends CarserviceApplicationTests {

  private static final Logger LOG = LoggerFactory.getLogger(GetAllCarsTest.class);

  @Test
  public void testGettingAllCars() {
    Response response = jsonRequest().get("/cars");
    List<Car> cars = asCars(response);
    assertThat(cars, hasSize(greaterThanOrEqualTo(3)));
    assertThat(cars, everyItem(hasProperty("id", greaterThan(0L))));
  }

  private List<Car> asCars(Response response) {
    response.then().statusCode(200);
    LOG.debug(response.body().prettyPrint());
    Car[] cars = response.as(Car[].class);
    return Arrays.asList(cars);
  }
}
