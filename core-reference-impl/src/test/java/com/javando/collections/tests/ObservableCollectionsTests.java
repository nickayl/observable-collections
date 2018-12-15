package com.javando.collections.tests;

import com.javando.collections.api.ObservableCollections;
import com.javando.collections.api.ObservableList;
import com.javando.collections.api.ObservableUnit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ObservableCollectionsTests {

    @Test
    public void getObservableUnit() {
        ObservableUnit<String> obsUnit = ObservableCollections.getObservableUnit();
        assertNotNull(obsUnit);
        obsUnit.setValue("");
        assertNotNull(obsUnit.getValue());
        assertEquals("", obsUnit.getValue());
    }

    @Test
    public void getObservableArrayList() throws Exception {
        ObservableList<String> observableList = ObservableCollections.newArrayList();
        assertNotNull(observableList);
    }

    @Test
    public void getObservableSortedList() throws Exception {
        ObservableList<String> observableList = ObservableCollections.newSortedArrayList();
        assertNotNull(observableList);
    }
}