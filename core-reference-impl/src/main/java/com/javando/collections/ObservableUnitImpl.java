package com.javando.collections;

import com.javando.collections.api.EventListener;
import com.javando.collections.api.EventType;
import com.javando.collections.api.ObservableUnit;

class ObservableUnitImpl<T> implements ObservableUnit<T>, Observer<T> {

    private ObservableObject<T> observable;
    private EventListener<T> changeEventListener;

    public ObservableUnitImpl() {
        observable = new ObservableValue<>();
        observable.registerObserver(this);
    }

    public ObservableUnitImpl(T objectToObserve) {
        this();
        setValue(objectToObserve);
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
    public void setValue(T object) {
        observable.setValue(object);
    }

    @Override
    public T getValue() {
        return observable.getValue();
    }


    @Override
    public void notifyChange(ObservableObject<T> observableValue, T value) {
        if(changeEventListener != null)
            changeEventListener.handleEvent(Events.newInstance(EventType.CHANGE_ELEMENT_EVENT, observableValue));
    }
}
