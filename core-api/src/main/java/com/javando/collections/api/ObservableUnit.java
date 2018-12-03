package com.javando.collections.api;

public interface ObservableUnit<T> {
    void setOnChangeEventListener(EventListener<T> changeEventListener);
    void removeEventListener();
    void setObservableObject(T object);
    Observable<T> getObservable();
}
