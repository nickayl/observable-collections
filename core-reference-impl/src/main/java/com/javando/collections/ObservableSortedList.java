package com.javando.collections;

import java.util.Collections;
import java.util.TreeMap;

class ObservableSortedList<V extends Comparable<V>> extends ObservableArrayList<V> {

    @Override
    protected void init() {
        super.init();
        super.observableValuesMap = new TreeMap<>();
        automaticallyFireEvents = true;
    }

    @Override
    public boolean add(V v) {
        boolean result = super.add(v);
        Collections.sort(this);
       // fireAddEvent(getObservableValue(size()-1));
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        Collections.sort(this);
        return result;
    }

    @Override
    public V set(int index, V element) {
        V result = super.set(index, element);
        Collections.sort(this);
        return result;
    }
}
