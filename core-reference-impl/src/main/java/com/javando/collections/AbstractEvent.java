package com.javando.collections;

import com.javando.collections.api.Event;
import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;

abstract class AbstractEvent<T> implements Event<T> {

    public abstract EventType getEventType();

    public abstract T getValue();

    public T getOldValue() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException();
    }

    public abstract Observable<T> getObservableValue();

    /**
     * Prevents the event from take effect. (For example, if you prevent an AddEvent from taking effect, no element will be added in the list)
     */
    public void preventConsume() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Prevent consume non supported on this derivative ()");
    }

    protected abstract boolean preventConsumeEnabled();

    /**
     * Returns the index of the new added element if it comes from an indexed data structure, like an array or an ArrayList. <code>{@link java.util.LinkedList}</code> does not support this method. <br><br>
     * For instance, if in an ObservableList a new element is added, it will fire an AddEvent event, which will be captured by all the listeners attached to the list. <br>
     * In this scenario, event will contain the index of the new added element
     *
     * @return the index of the element in which the event occurred, -1 if the list does not support this method (a non-indexed data structure).
     */
    public abstract int getIndex();

    @Override
    public String toString() {
        return String.format("\n ====================== Event ================ \n ===       Type:=== *** %s *** \n ===     value: === *** %s ***\n =================== End Event ===============",
                getEventType().name(), getValue());
    }
}
