package com.javando.collections.api;

/**
 *
 * @param <T>
 */
@FunctionalInterface
public interface EventListener<T> {
    void handleEvent(Event<T> event);
}
