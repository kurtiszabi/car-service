package com.gmail.at.kurtiszabi.repositories;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Repository<T> {

  protected final AtomicLong id = new AtomicLong();

  protected final ConcurrentHashMap<Long, T> store = new ConcurrentHashMap<>();

}
