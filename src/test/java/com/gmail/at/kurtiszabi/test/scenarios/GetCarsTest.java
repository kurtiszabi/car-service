package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarDetails;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class GetCarsTest extends CarserviceApplicationTests {

  private static final long KNOWN_CAR_ID = 1L;

  public static final Logger LOG = LoggerFactory.getLogger(GetCarsTest.class);

  @Test
  public void testGettingASingleCar() {
    Car car = getCarById(KNOWN_CAR_ID);
    assertCar(car);
  }

  @Test
  public void testGettingAllCars() {
    List<Car> cars = getAllCars();
    assertThat(cars, hasSize(greaterThanOrEqualTo(3)));
    assertThat(cars, everyItem(hasProperty("id", greaterThan(0L))));
  }
  
  @Test
  public void testGettingANonExistingCar() {
    Response response = jsonRequest().get("/cars/{id}", 0);
    response.then().statusCode(404);
    assertThat(response.getBody().jsonPath().getString("message"),
        Matchers.containsString("No such car"));
  }

  private void assertCar(Car car) {
    assertThat(car.getId(), equalTo(KNOWN_CAR_ID));
    assertThat(car.getDetails(), notNullValue());
    CarDetails details = car.getDetails();
    assertThat(details.getManufacturer(), equalTo("Toyota"));
    assertThat(details.getColor(), equalTo("white"));
    assertThat(details.getModel(), equalTo("RAV4"));
    assertThat(details.getManufacuted(), equalTo(LocalDate.of(2017, 2, 25)));
  }
}
