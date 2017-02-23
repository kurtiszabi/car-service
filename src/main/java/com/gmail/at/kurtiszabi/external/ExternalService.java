package com.gmail.at.kurtiszabi.external;

import com.gmail.at.kurtiszabi.domain.CarDetails;

public interface ExternalService {

  boolean isPermitted(CarDetails details, String country) throws InterruptedException;

}
