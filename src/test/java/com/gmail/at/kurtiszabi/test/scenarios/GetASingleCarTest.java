package com.gmail.at.kurtiszabi.test.scenarios;

import org.junit.Test;

import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class GetASingleCarTest extends CarserviceApplicationTests {

  @Test
  public void testGettingASingleCar() {
    Response response = jsonRequest().get("/cars/{id}", 1);
    response.then().statusCode(200);

  }

}
