package com.gmail.at.kurtiszabi.test.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assume.assumeThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SaveReservationTest extends CarserviceApplicationTests {

  private static final String CUSTOMER_NAME = "Szabolcs Kurti";

  private static final long KNOWN_CAR_ID = 1L;

  private static final long DACIA_CAR_ID = 2L;

  private static long reservationId;

  private static LocalDateTime from;

  private static LocalDateTime to;

  @Test
  public void A_testSavingAReservation() {
    CarReservation saved = saveReservation(createDefaultReservation());
    assertThat(saved.getId(), greaterThan(0L));
    reservationId = saved.getId();
  }

  private CarReservation createDefaultReservation() {
    CarReservation res = new CarReservation();
    res.setCar(getCarById(KNOWN_CAR_ID));
    res.setUser(CUSTOMER_NAME);
    LocalDateTime now = LocalDateTime.now();
    from = now.plusDays(1);
    res.setFrom(from);
    to = now.plusDays(2);
    res.setTo(to);
    res.setCountry("HU");
    return res;
  }

  @Test
  public void B_testGettingBackReservations() {
    assumeThat(reservationId, greaterThan(0L));
    List<CarReservation> reservations = getReservationsByCarId(KNOWN_CAR_ID);
    assertThat(reservations, is(not(empty())));
    CarReservation reservation = findReservation(reservations, reservationId);
    assertReservation(reservation);
  }

  private CarReservation findReservation(List<CarReservation> reservations, long reservationId2) {
    Optional<CarReservation> reservation =
        reservations.stream().filter(r -> Objects.equals(r.getId(), reservationId)).findFirst();
    assertThat(reservation.isPresent(), equalTo(true));
    return reservation.get();
  }

  private void assertReservation(CarReservation r) {
    assertThat(r.getCar(), notNullValue());
    assertThat(r.getId(), greaterThan(0L));
    assertThat(r.getUser(), equalTo(CUSTOMER_NAME));
    assertThat(r.getFrom(), equalTo(from));
    assertThat(r.getTo(), equalTo(to));
  }

  @Test
  public void C_testTrySavingEnvelopedReservation() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation reservation = createDefaultReservation();
    reservation.setFrom(from.plusHours(1));
    reservation.setTo(to.minusHours(1));
    Response response = trySavingReservation(reservation);
    assertAlreadyReserved(response);
  }

  @Test
  public void D_testTrySavingEnvelopingReservation() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation reservation = createDefaultReservation();
    reservation.setFrom(from.minusHours(1));
    reservation.setTo(to.plusHours(1));
    Response response = trySavingReservation(reservation);
    assertAlreadyReserved(response);
  }

  @Test
  public void E_testTrySavingReservationInterleavingFromBottom() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation reservation = createDefaultReservation();
    reservation.setFrom(from.minusDays(1));
    reservation.setTo(from.plusHours(1));
    Response response = trySavingReservation(reservation);
    assertAlreadyReserved(response);
  }

  @Test
  public void F_testTrySavingReservationInterleavingFromTop() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation reservation = createDefaultReservation();
    reservation.setFrom(to.minusHours(1));
    reservation.setTo(to.plusDays(1));
    Response response = trySavingReservation(reservation);
    assertAlreadyReserved(response);
  }

  @Test
  public void H_testSavingReservationBeforeAnExistingOne() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation res = createDefaultReservation();
    res.setFrom(from.minusDays(2));
    res.setTo(from.minusDays(1));
    saveReservation(res);
  }

  @Test
  public void I_testSavingReservationAfterAnExistingOne() {
    assumeThat(reservationId, greaterThan(0L));
    CarReservation res = createDefaultReservation();
    res.setFrom(to.plusDays(1));
    res.setTo(to.plusDays(2));
    saveReservation(res);
  }

  @Test
  public void G_testSavingReservationForANonExistingCar() {
    CarReservation res = createDefaultReservation();
    Car nonExistingCar = new Car();
    nonExistingCar.setId(0L);
    res.setCar(nonExistingCar);
    Response response = trySavingReservation(res);
    response.then().statusCode(400);
    assertThat(response.body().jsonPath().getString("message"),
        allOf(containsString("Reservation failed"), containsString("no such car")));
  }

  @Test
  public void J_testSavingReservationWithAnIllegalCombinationOfCarAndCountry() {
    Car dacia = getCarById(DACIA_CAR_ID);
    assumeThat(dacia.getDetails().getManufacturer(), equalTo("Dacia"));
    CarReservation reservation = createDefaultReservation();
    reservation.setCar(dacia);
    reservation.setCountry("US");
    Response response = trySavingReservation(reservation);
    assertNotPermitted(response);
  }

  private void assertNotPermitted(Response response) {
    response.then().statusCode(400);
    assertThat(response.body().jsonPath().getString("message"), allOf(
        containsString("Reservation failed"), containsString("selected car is not permitted")));
  }

  private void assertAlreadyReserved(Response response) {
    response.then().statusCode(400);
    assertThat(response.body().jsonPath().getString("message"),
        allOf(containsString("Reservation failed"), containsString("already booked")));
  }

  private CarReservation saveReservation(CarReservation res) {
    Response response = trySavingReservation(res);
    response.then().statusCode(200);
    return response.as(CarReservation.class);
  }
}
