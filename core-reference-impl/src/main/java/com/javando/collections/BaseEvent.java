package com.javando.collections;

import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;


class BaseEvent<V> extends AbstractEvent<V> {

    private EventType eventType;
    private ObservableObject<V> value;
    private IndexedSource<V> indexedSource;

    private boolean preventConsume=false;

    public BaseEvent() {
    }

    protected BaseEvent(EventType eventType, ObservableObject<V> value) {
        this.eventType = eventType;
        this.value = value;
    }

    public BaseEvent(EventType eventType, ObservableObject<V> value, IndexedSource<V> indexedSource) {
        this.eventType = eventType;
        this.value = value;
        this.indexedSource = indexedSource;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setValue(ObservableObject<V> value) {
        this.value = value;
    }

    public void setIndexedSource(IndexedSource<V> indexedSource) {
        this.indexedSource = indexedSource;
    }

    @Override
    public EventType getEventType() {
        return eventType;
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
    public void preventConsume() throws UnsupportedOperationException {
        if(!isSupported(eventType))
            super.preventConsume();


        this.preventConsume = true;
    }

    @Override
    protected boolean preventConsumeEnabled() {
        return preventConsume;
    }


    @Override
    public int getIndex() {
        if(indexedSource != null)
            return indexedSource.getIndexOf(value.getValue());
        return -1;
    }

    private boolean isSupported(EventType eventType) {
        return eventType.equals(EventType.ADD_ELEMENT_EVENT);
    }

    //    @Override
//    public ObservableValue<V> getObservableValue() {
//        return obsList.get(index);
//    }

}
