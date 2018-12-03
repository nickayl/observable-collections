package com.javando.collections.tests;

import com.javando.collections.InstanceResolver;
import com.javando.collections.api.ObservableUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;


public class ObservableUnitImplTests {

    private static ObservableUnit<String> obs;
  //  private static final Logger log = LoggerFactory.getLogger(ObservableUnitImplTests.class);


    @BeforeClass
    public static void before() {
        obs = InstanceResolver.newObservableUnit();
        obs.setObservableObject("Ciao polli");
    }

    @Test(expected = java.lang.AssertionError.class)
    public void eventHandlerCalledTest() {
        obs.setOnChangeEventListener(event -> fail());
        obs.getObservable().setValue("Pollacci!");
    }

    @Test
    public void eventValueCorrectTest() {
        obs.setOnChangeEventListener(event -> System.out.format("event: %s", event));
        obs.getObservable().setValue("superpolli");
    }

}