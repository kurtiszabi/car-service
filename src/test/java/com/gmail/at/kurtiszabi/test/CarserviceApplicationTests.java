package com.gmail.at.kurtiszabi.test;

import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class CarserviceApplicationTests {

  @LocalServerPort
  private String port;

  protected RequestSpecification jsonRequest() {
    RestAssured.port = Integer.parseInt(port);
    return RestAssured.given().log().all().accept(ContentType.JSON);
  }

  protected RequestSpecification jsonRequest(Object object) {
    return jsonRequest().contentType(ContentType.JSON).body(object);
  }



}
