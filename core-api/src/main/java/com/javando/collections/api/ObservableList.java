package com.javando.collections.api;

import java.util.List;

public interface ObservableList<V> extends ObservableCollection<V>, List<V>  {
    void addOnChangeEventListener(EventListener<V> changeEventListener);
    void addOnAddEventListener(EventListener<V> addEventListener);
    void addOnRemoveEventListener(EventListener<V> removeEventListener);
    void addOnGetEventListener(EventListener<V> onGetEventListener);
    Observable<V> getObservableValue(int index);
    Iterable<? extends Observable<V>> iterate();
}
