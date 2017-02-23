package com.gmail.at.kurtiszabi.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractUnitTestBase<T> {

  protected Class<T> clazz =
      (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  protected T classUnderTest;

  public T createInstance() {
    try {
      return clazz.getConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to instantiate class", e);
    }
  }

  @Before
  public void init() {
    classUnderTest = createInstance();
  }

  @Test
  public void tesThatTheyAreInTheSamePackage() {
    assertThat(this.getClass().getPackage().getName(), equalTo(clazz.getPackage().getName()));
  }

  @Test
  public void testClassName() {
    assertThat(this.getClass().getSimpleName(), equalTo(clazz.getSimpleName() + "Test"));
  }

}
