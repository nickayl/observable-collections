package com.javando.collections;

import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;

class RemoveEvent<T> extends AbstractEvent<T> {

    private ObservableObject<T> obsValue;
    private IndexedSource<T> indexedSource;
    private boolean preventConsume = false;

    public RemoveEvent(ObservableObject<T> obsValue) {
        this.obsValue = obsValue;
    }

    public RemoveEvent(ObservableObject<T> obsValue, IndexedSource<T> indexedSource) {
        this.obsValue = obsValue;
        this.indexedSource = indexedSource;
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVE_ELEMENT_EVENT;
    }

    @Override
    public T getValue() {
        return obsValue.getValue();
    }

    @Override
    public Observable<T> getObservableValue() {
        return obsValue;
    }

//    @Override
//    public void preventConsume() {
//        this.preventConsume = true;
//    }

    @Override
    protected boolean preventConsumeEnabled() {
        return preventConsume;
    }

    @Override
    public int getIndex() {
        if (indexedSource != null)
            return indexedSource.getIndexOf(obsValue.getValue());

        return -1;
    }
}
