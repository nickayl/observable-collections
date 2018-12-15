package com.javando.collections;

import com.javando.collections.api.EventType;

class Events {

    private static EventRecycler recycler = new EventRecycler();

    public static <T> AbstractEvent<T> newChangeEvent(ObservableObject<T> newValue, T oldValue, IndexedSource<T> indexedSource) {
        return Events.newInstance(EventType.CHANGE_ELEMENT_EVENT, newValue, oldValue, indexedSource);
        //  return new ChangeEvent<>(newValue, oldValue, indexedSource);
    }

    public static <T> AbstractEvent<T> newChangeEvent(ObservableObject<T> newValue, T oldValue) {
        return Events.newInstance(EventType.CHANGE_ELEMENT_EVENT, newValue, oldValue,null);
//        return new ChangeEvent<>(newValue, oldValue);
    }

    public static <T> AbstractEvent<T> newAddEvent(ObservableObject<T> value) {
        return Events.newInstance(EventType.ADD_ELEMENT_EVENT, value);
    }

    public static <T> AbstractEvent<T> newAddEvent(ObservableObject<T> value, IndexedSource<T> indexedSource) {
        return Events.newInstance(EventType.ADD_ELEMENT_EVENT, value, indexedSource);
    }

    private static <T> AbstractEvent<T> newInstance(EventType eventType, ObservableObject<T> newValue, T oldValue, IndexedSource<T> indexedSource) {
        return recycler.getRecycledChangeEvent(eventType, newValue, oldValue, indexedSource);
    }

    static <V> AbstractEvent<V> newInstance(EventType eventType, ObservableObject<V> observableValue) {
        // return new BaseEvent<>(eventType, observableValue);
        return recycler.getRecycledEvent(eventType, observableValue);
    }

    static <V> AbstractEvent<V> newInstance(EventType eventType, ObservableObject<V> observableValue, IndexedSource<V> indexedSource) {
        //  return new BaseEvent<>(eventType, observableValue, indexedSource);
        return recycler.getRecycledEvent(eventType, observableValue, indexedSource);
    }

    private static class EventRecycler<T> {

        private BaseEvent<T> baseEvent = new BaseEvent<>();
        private ChangeEvent<T> changeEvent = new ChangeEvent<>();

        AbstractEvent<T> getRecycledEvent(EventType eventType, ObservableObject<T> observableValue, IndexedSource<T> indexedSource) {
            getRecycledEvent(eventType, observableValue);
            baseEvent.setIndexedSource(indexedSource);
            return baseEvent;
        }

        AbstractEvent<T> getRecycledEvent(EventType eventType, ObservableObject<T> observableValue) {
            baseEvent.reset();
            baseEvent.setEventType(eventType);
            baseEvent.setValue(observableValue);
            return baseEvent;
        }

        AbstractEvent<T> getRecycledChangeEvent(EventType eventType, ObservableObject<T> newValue, T oldValue, IndexedSource<T> indexedSource) {
            changeEvent.setIndexedSource(indexedSource);
            changeEvent.setValue(newValue);
            changeEvent.setOldValue(oldValue);

            return changeEvent;
        }

    }

}
