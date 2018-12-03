package com.javando.collections.api;

public interface Event<T> {

    EventType getEventType();

    T getValue();

    T getOldValue();

    Observable<T> getObservableValue();

    /**
     * Prevents the event from take effect. (For example, if you prevent an AddEvent from taking effect, no element will be added in the list)
     */
    void preventConsume() throws UnsupportedOperationException;

    /**
     * Returns the index of the new added element if it comes from an indexed data structure, like an array or an ArrayList. <code>{@link java.util.LinkedList}</code> does not support this method. <br><br>
     * For instance, if in an ObservableList a new element is added, it will fire an AddEvent event, which will be captured by all the listeners attached to the list. <br>
     * In this scenario, event will contain the index of the new added element
     *
     * @return the index of the element in which the event occurred, -1 if the list does not support this method (a non-indexed data structure).
     */
    int getIndex();

    // Checked exception ----
    class UnsupportedOperationException extends Exception {

        public UnsupportedOperationException() {
        }

        public UnsupportedOperationException(String message) {
            super(message);
        }
    }
}
