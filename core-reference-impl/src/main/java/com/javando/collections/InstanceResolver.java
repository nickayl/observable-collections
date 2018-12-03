package com.javando.collections;

import com.javando.collections.api.ObservableList;
import com.javando.collections.api.ObservableUnit;

public class InstanceResolver {

    public static <T> ObservableList<T> newObservableArrayList() {
        return new ObservableArrayList<>();
    }

    public static <T extends Comparable<T>> ObservableList<T> newSortedArrayList() {

        return new ObservableSortedList<>();
    }

   public static <T> ObservableUnit<T> newObservableUnit() {
        return new ObservableUnitImpl<>();
   }
}
