package com.javando.collections;

import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;


class ChangeEvent<V> extends AbstractEvent<V> {

    private ObservableObject<V> value;
    private V oldValue;
    private IndexedSource<V> indexedSource;


    private boolean preventConsume = false;

    protected ChangeEvent(ObservableObject<V> newValue, V oldValue) {
        this.value = newValue;
        this.oldValue = oldValue;
    }

    public ChangeEvent(ObservableObject<V> value, V oldValue, IndexedSource<V> indexedSource) {
        this.value = value;
        this.oldValue = oldValue;
        this.indexedSource = indexedSource;
    }

    public void setValue(ObservableObject<V> value) {
        this.value = value;
    }

    public void setOldValue(V oldValue) {
        this.oldValue = oldValue;
    }

    public void setIndexedSource(IndexedSource<V> indexedSource) {
        this.indexedSource = indexedSource;
    }

    public ChangeEvent() {

    }

    @Override
    public EventType getEventType() {
        return EventType.CHANGE_ELEMENT_EVENT;
    }

    @Override
    public V getValue() {
        return value.getValue();
    }

    @Override
    public V getOldValue() {
        return oldValue;
    }

    @Override
    public Observable<V> getObservableValue() {
        return value;
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
            return indexedSource.getIndexOf(value.getValue());

        return -1;
    }

    //    @Override
//    public ObservableValue<V> getObservableValue() {
//        return obsList.get(index);
//    }

}
