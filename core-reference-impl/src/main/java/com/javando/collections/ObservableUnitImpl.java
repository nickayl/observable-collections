package com.javando.collections;

import com.javando.collections.api.EventListener;
import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;
import com.javando.collections.api.ObservableUnit;

class ObservableUnitImpl<T> implements ObservableUnit<T>, Observer<T> {

    private ObservableObject<T> observable;
    private EventListener<T> changeEventListener;

    public ObservableUnitImpl() {
    }

    public ObservableUnitImpl(T objectToObserve) {
        setObservableObject(objectToObserve);
    }

    @Override
    public void setOnChangeEventListener(EventListener<T> changeEventListener) {
        this.changeEventListener = changeEventListener;
    }

    @Override
    public void removeEventListener() {
        this.changeEventListener = null;
    }

    @Override
    public void setObservableObject(T object) {
        if(observable == null) {
            observable = new ObservableValue<>(object);
            observable.registerObserver(this);
        } else {
            observable.setValue(object);
        }
    }

    @Override
    public Observable<T> getObservable() {
        return observable;
    }

    @Override
    public void notifyChange(ObservableObject<T> observableValue, T value) {
        changeEventListener.handleEvent(Events.newInstance(EventType.CHANGE_ELEMENT_EVENT, observableValue));
    }
}
