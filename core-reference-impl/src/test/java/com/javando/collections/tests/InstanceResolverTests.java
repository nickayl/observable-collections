package com.javando.collections.tests;

import com.javando.collections.InstanceResolver;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class InstanceResolverTests {


    @Test
    public void newObservableArrayList() {
        assertThat(InstanceResolver.newObservableArrayList(), is(not(nullValue())));
    }

    @Test
    public void newSortedArrayList() {
        assertThat(InstanceResolver.newSortedArrayList(), is(not(nullValue())));
    }

    @Test
    public void newObservableUnit() {
        assertThat(InstanceResolver.newObservableUnit(), is(not(nullValue())));
    }
}