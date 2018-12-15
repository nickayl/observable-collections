package com.javando.collections.api;

import java.util.Collection;

public interface ObservableCollection<T> extends Collection<T> {

    void addEventListener(EventListener<T> eventListener);
    void addEventListener(String key, EventListener<T> eventListener);

    void removeEventListener(EventListener<T> eventListener);
    void removeEventListener(String key);

    void removeAllListeners();

    void sout();
}
