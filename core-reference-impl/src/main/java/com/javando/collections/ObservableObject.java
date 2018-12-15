package com.javando.collections;

import com.javando.collections.api.Observable;

abstract class ObservableObject<T> implements Observable<T> {

    protected abstract void registerObserver(Observer<T> observer);

    protected abstract void unregisterObserver(Observer<T> observer);
}
