package com.javando.collections.api;

public interface Observable<T> {

    void setValue(T newValue);

    T getValue();
}
