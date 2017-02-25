package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarDetails;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class GetASingleCarTest extends CarserviceApplicationTests {

  private static final long KNOWN_CAR_ID = 1L;

  @Test
  public void testGettingASingleCar() {
    Car car = getCarById(KNOWN_CAR_ID);
    assertCar(car);
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
