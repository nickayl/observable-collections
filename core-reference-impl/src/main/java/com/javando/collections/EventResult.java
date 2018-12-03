package com.javando.collections;

@FunctionalInterface
interface EventResult<T> {
    void onEventHandled(T event);
}
