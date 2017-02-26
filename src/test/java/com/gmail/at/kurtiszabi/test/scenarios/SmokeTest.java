package com.gmail.at.kurtiszabi.test.scenarios;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.at.kurtiszabi.domain.Car;
import com.gmail.at.kurtiszabi.domain.CarReservation;
import com.gmail.at.kurtiszabi.test.CarserviceApplicationTests;

import io.restassured.response.Response;

public class SmokeTest extends CarserviceApplicationTests {

  private static final Logger LOG = LoggerFactory.getLogger(SmokeTest.class);

  private static final long RESPONSE_TIME_THRESHOLD = 4000L;

  private static final int THREAD_POOL_SIZE = 5;

  private static final int RESERVATION_COUNT_PER_CAR = 10;

  private LocalDateTime now = LocalDateTime.now();

  private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

  @Test
  public void smokeTest() throws InterruptedException, ExecutionException {
    List<Callable<Long>> tasks = createReservationTasks();
    List<Future<Long>> futures = executor.invokeAll(tasks);
    List<Long> durations = futures.stream().map(f -> safeGet(f)).collect(toList());
    LongSummaryStatistics statistics = toLongStream(durations).summaryStatistics();
    LOG.info("Tasks finshed {}", statistics);
    assertThat(statistics.getMax(), lessThan(RESPONSE_TIME_THRESHOLD));
  }

  private LongStream toLongStream(List<Long> list) {
    return list.stream().mapToLong((v) -> {
      return (long) v;
    });
  }

  private List<Callable<Long>> createReservationTasks() {
    List<Car> cars = getAllCars();
    List<Callable<Long>> tasks = new ArrayList<>(cars.size() * RESERVATION_COUNT_PER_CAR);
    cars.stream().forEach(car -> {
      IntStream.range(0, RESERVATION_COUNT_PER_CAR).forEach((i) -> {
        tasks.add(new Callable<Long>() {
          @Override
          public Long call() throws Exception {
            CarReservation r = new CarReservation();
            r.setCar(car);
            r.setCountry("SK");
            r.setUser("Szabolcs Kurti");
            r.setFrom(now.plusYears(1).plusHours((i * 6) + 1));
            r.setTo(r.getFrom().plusHours(5));
            long started = System.currentTimeMillis();
            Response response = trySavingReservation(r);
            LOG.info("Reservation {} returned with status {}", r, response.getStatusCode());
            return System.currentTimeMillis() - started;
          }
        });
      });
    });
    return tasks;
  }

}
