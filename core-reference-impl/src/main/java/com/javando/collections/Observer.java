package com.javando.collections;

interface Observer<T> {
    void notifyChange(ObservableObject<T> observableValue, T value);
}
