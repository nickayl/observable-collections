package com.javando.collections;

import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;

class AddEvent<V> extends AbstractEvent<V> {

    private ObservableValue<V> value;
    private boolean preventConsume = false;

    protected AddEvent(ObservableValue<V> value) {
        this.value = value;
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD_ELEMENT_EVENT;
    }

    @Override
    public V getValue() {
        return value.getValue();
    }

    @Override
    public Observable<V> getObservableValue() {
        return value;
    }

    @Override
    public void preventConsume() {
        this.preventConsume = true;
    }

    @Override
    protected boolean preventConsumeEnabled() {
        return preventConsume;
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
