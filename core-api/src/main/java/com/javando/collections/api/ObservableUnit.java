package com.javando.collections.api;

public interface ObservableUnit<T> {
    void setOnChangeEventListener(EventListener<T> changeEventListener);
    void removeEventListener();
    void setValue(T object);
    T getValue();
}
