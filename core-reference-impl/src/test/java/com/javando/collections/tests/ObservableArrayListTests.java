package com.javando.collections.tests;

import com.javando.collections.api.Event;
import com.javando.collections.api.EventListener;
import com.javando.collections.api.ObservableCollections;
import com.javando.collections.api.ObservableList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;


public class ObservableArrayListTests {

    //  private static final Logger log = LoggerFactory.getLogger(ObservableArrayListTests.class);

    private static ObservableList<String> obsArrayList = ObservableCollections.newArrayList();
    private static EventListener<String> addEventListener;
    private boolean eventCalled;


    @Before
    public void reset() {
        System.out.println("Reset called");
        eventCalled = false;
        obsArrayList.removeAllListeners();
    }

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("setup called");
        obsArrayList.addAll(Arrays.asList("Domenico", "Chiara", "Fiorella", "Lorenzo", "Giuseppe", "Maria Rosa"));
    }

    @Test
    public void testAddEventListenerCalled() {
        obsArrayList.addOnAddEventListener(event -> {
            eventCalled = true;
            System.out.println("Element added: " + event.getEventType().name());
        });

        obsArrayList.add("Giuliana");
        assertTrue(eventCalled);
        obsArrayList.sout();
    }

    @Test
    public void testModifyElementEventListenerCalled() {
        obsArrayList.addOnChangeEventListener(event -> eventCalled = true);
        obsArrayList.sout();

        obsArrayList.getObservableValue(1).setValue("Chiaretta");

        String expected = "Chiaretta";
        Object actual = obsArrayList.getObservableValue(1).getValue();
        assertEquals(expected, actual);
        assertTrue(eventCalled);

        eventCalled = false;

        obsArrayList.set(0, "Gabriele");
        expected = "Gabriele";
        actual = obsArrayList.getObservableValue(0).getValue();
        assertEquals(expected, actual);
        assertTrue(eventCalled);

        obsArrayList.sout();

    }

    @Test
    public void preventConsume__ADD__Test() {

        obsArrayList.addOnAddEventListener(event -> {
            //log.debug("element added: {}", event.getValue());
            System.out.printf("element added: %s", event.getValue());

            if (event.getValue().equals("pollo")) {
                try {
                    event.preventConsume();
                } catch (Event.UnsupportedOperationException e) {
                    e.printStackTrace();
                }
            }

        });

        obsArrayList.add("ciaociao");
        int expectedSize = obsArrayList.size();

        obsArrayList.add("pollo");

        assertThat(obsArrayList.size(), is(expectedSize));
        boolean match = obsArrayList.contains("pollo"); //obsArrayList.stream().anyMatch(s -> s.equals("pollo"));
        assertThat(match, not(true));
        obsArrayList.sout();
    }

    @Test(expected = AssertionError.class)
    public void preventConsume__REMOVE__TEST() {
        obsArrayList.addOnRemoveEventListener(new EventListener<String>() {
            @Override
            public void handleEvent(Event<String> event) {
                try {
                    event.preventConsume();
                } catch (Event.UnsupportedOperationException e) {
                    fail();
                }
            }
        });

        obsArrayList.remove("Domenico");
    }


    @Test
    public void removeElementTest() {
        obsArrayList.addOnRemoveEventListener(event -> {
            if (event == null)
                return;
            System.out.printf("Event object: %s", event);
            System.out.printf("Element removed: %s", event.getValue());
            //log.debug("Element removed: {}",event.getValue());
            //log.debug("Event object: {}", event);
            eventCalled = true;
        });

        String value = "Domenico";
        int indexOf = obsArrayList.indexOf(value);
        int oldSize = obsArrayList.size();
        obsArrayList.remove(value);
        // === Tests === //
        assertThat(obsArrayList.get(indexOf), is(not(value)));
        assertThat(obsArrayList.size(), is(lessThan(oldSize)));
        assertTrue(eventCalled);


    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.out.println("Teardown called");
    }
}