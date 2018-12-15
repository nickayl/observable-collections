package com.javando.collections;

import com.javando.collections.api.EventListener;
import com.javando.collections.api.EventType;
import com.javando.collections.api.Observable;
import com.javando.collections.api.ObservableList;
import com.javando.collections.internal.Benchmark;

import java.util.*;
import java.util.concurrent.TimeUnit;


class ObservableArrayList<V> extends ArrayList<V> implements ObservableList<V>, IndexedSource<V>, Observer<V> {

    // private static final Logger log = LoggerFactory.getLogger(ObservableArrayList.class);

    private Map<String, EventListener<V>> eventListeners = new HashMap<>();
    Map<V, ObservableObject<V>> observableValuesMap;

    private Benchmark benchmark = new Benchmark();

    // Categorized Event Listeners --------- //
    private List<EventListener<V>> onChangeEventListeners = new ArrayList<>();
    private List<EventListener<V>> onAddEventListeners = new ArrayList<>();
    private List<EventListener<V>> onRemoveEventListeners = new ArrayList<>();
    private List<EventListener<V>> onGetEventListeners = new ArrayList<>();
    // ------------------------------------ //

    //  private List<ObservableObject<V>> observableListValues = new ArrayList<>();

    private int index = 0;
    boolean automaticallyFireEvents = true;

    public ObservableArrayList() {
        init();
    }

    public ObservableArrayList(int initialCapacity) {
        super(initialCapacity);
        init();
    }

    public ObservableArrayList(Collection<? extends V> c) {
        super(c);
        System.out.println("Constructor with collection called in observablearraylist");
        init();
        wrapCollection(c, true);
    }

    protected void init() {
        observableValuesMap = new HashMap<>();
        benchmark.setTimeUnit(TimeUnit.SECONDS);
    }

    @Override
    public boolean add(final V t) {
        // logger.debug(" LOG4J - add called");
        boolean result = super.add(t);
        final ObservableObject<V> wrappedValue = wrapValue(t);

        if (automaticallyFireEvents) {
            fireAddEvent(wrappedValue, new EventResult<AbstractEvent<V>>() {
                @Override
                public void onEventHandled(AbstractEvent<V> event) {
                    //log.debug("AbstractEvent {} fired. Value added: {}", event.getEventType(), t.toString());
                    System.out.format("AbstractEvent %s fired. Value added: %s", event.getEventType().name(), t.toString());
                    if (event.preventConsumeEnabled()) {
                        //log.debug("Prevent consume on add element: {}", t);
                        System.out.format("Prevent consume on add element: %s", t);
                        wrappedValue.unregisterObserver(ObservableArrayList.this);
                        observableValuesMap.remove(t);
                        remove(t);
                    }

                }
            });
        }
        return result;
    }

    @Override
    public V set(int index, V element) {
        // Create new wrapper for the updated element
        ObservableObject<V> newValue = new ObservableValue<>(element);
        // getting the old element
        V oldValue = get(index);

        observableValuesMap.remove(oldValue);
        observableValuesMap.put(element, newValue);

        V returnValue = super.set(index, element);
        fireChangeEvent(newValue, oldValue);

        return returnValue;
    }

    @Override
    public boolean remove(Object o) {

        if (o == null)
            throw new NullPointerException("Cannot remove element - argument cannot be null");

        if (size() == 0)
            return false;

        if (!o.getClass().equals(get(0).getClass())) {
            throw new IllegalArgumentException("Cannot remove element - Illegal argument given: " + o.getClass());
        }

        if(indexOf(o) == -1) {
            System.out.format("\n\n*** WARNING *** ---> Trying to remove an unexistent element: %s <---\n\n",o);
            return false;
        }

        final boolean[] shouldStop = {false};

        fireTargetEvent(EventType.REMOVE_ELEMENT_EVENT, observableValuesMap.get(o), event -> {
            if (event.preventConsumeEnabled())
                shouldStop[0] = true;
        });

        if (shouldStop[0])
            return false;

        observableValuesMap.remove(o);
        return super.remove(o);

    }

    @Override
    public V remove(int index) {
        V element = get(index);
        remove(element);
        return element;
    }

    private void wrapCollection(Collection<? extends V> c, boolean fireEvents) {
        for (V element : c) {
            ObservableObject<V> wrappedValue = wrapValue(element);
            if (fireEvents)
                fireAddEvent(wrappedValue, null);
        }
    }

    private ObservableObject<V> wrapValue(V t) {
        ObservableObject<V> wrappedValue = new ObservableValue<>(t, indexOf(t));
        wrappedValue.registerObserver(this);
        observableValuesMap.put(t, wrappedValue);
        return wrappedValue;
    }

    private void fireAddEvent(ObservableObject<V> valueChanged, EventResult<AbstractEvent<V>> eventResult) {
        benchmark.start();

        for (EventListener<V> eventListener : onAddEventListeners) {
            AbstractEvent<V> event = Events.newAddEvent(valueChanged, this);

            eventListener.handleEvent(event);

            if (eventResult != null) {
                eventResult.onEventHandled(event);
            }
        }

        fireGlobalEvents(valueChanged, null);

        benchmark.stop();
        benchmark.logResults();
    }

    private void fireChangeEvent(ObservableObject<V> valueChanged, V oldValue) {
        for (EventListener<V> eventListener : onChangeEventListeners) {
            eventListener.handleEvent(Events.newChangeEvent(valueChanged, oldValue, this));
        }

        fireGlobalEvents(valueChanged, oldValue);
    }

    private void fireGlobalEvents(ObservableObject<V> valueChanged, V oldValue) {
        for (EventListener<V> eventListener : eventListeners.values()) {
            eventListener.handleEvent(Events.newChangeEvent(valueChanged, oldValue, this));
        }
    }

    private void fireTargetEvent(EventType eventType, ObservableObject<V> element, EventResult<AbstractEvent<V>> eventResult) {
        benchmark.start();

        for (EventListener<V> eventListener : getEventList(eventType)) {
            AbstractEvent<V> event = Events.newInstance(eventType, element);

            eventListener.handleEvent(event);

            if (eventResult != null) {
                eventResult.onEventHandled(event);
            }
        }

        fireGlobalEvents(element, null);

        benchmark.stop();
        benchmark.logResults();
    }

//    private void fireRemoveEvent(Object o) {
//        for (EventListener<V> eventListener : onRemoveEventListeners) {
//            eventListener.handleEvent(Events.newInstance(EventType.REMOVE_ELEMENT_EVENT,
//                    observableValuesMap.get(o),
//                    this));
//        }
//    }


    @Override
    public void addEventListener(EventListener<V> eventListener) {
        addEventListener(String.valueOf(index++), eventListener);
    }

    @Override
    public void addEventListener(String key, EventListener<V> eventListener) {
        eventListeners.put(key, eventListener);
    }

    @Override
    public void removeEventListener(EventListener<V> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void removeEventListener(String key) {
        eventListeners.remove(key);
    }

    @Override
    public void removeAllListeners() {
        eventListeners.clear();
        onRemoveEventListeners.clear();
        onAddEventListeners.clear();
        onChangeEventListeners.clear();
        onGetEventListeners.clear();
    }

    @Override
    public void sout() {
        System.out.println(this);
    }

    @Override
    public void addOnChangeEventListener(EventListener<V> changeEventListener) {
        onChangeEventListeners.add(changeEventListener);
    }

    @Override
    public void addOnAddEventListener(EventListener<V> addEventListener) {
        onAddEventListeners.add(addEventListener);
    }

    @Override
    public void addOnRemoveEventListener(EventListener<V> removeEventListener) {
        this.onRemoveEventListeners.add(removeEventListener);
    }

    @Override
    public void addOnGetEventListener(EventListener<V> onGetEventListener) {
        this.onGetEventListeners.add(onGetEventListener);
    }

    @Override
    public Observable<V> getObservableValue(int index) {
        return observableValuesMap.get(get(index));
    }

    @Override
    public Iterable<ObservableObject<V>> iterate() {
        return new IterableKlass();
    }


    @Override
    public boolean addAll(Collection<? extends V> c) {
        return addAll(c, true);
    }

    public boolean addAll(Collection<? extends V> c, boolean silentAddEvent) {
        System.out.println("Add all called");
        boolean res = super.addAll(c);
        wrapCollection(c, silentAddEvent);
        return res;
    }


    // ---- Observer method impl ---- //
    @Override
    public void notifyChange(ObservableObject<V> observable, V oldValue) {
        System.out.format("Element changed. Index of %s: %d\n", observable.getValue(), index);
        set(indexOf(oldValue), observable.getValue());
    }


    // -------------------------------------- //

    /**
     * IndexedSource implementation
     *
     * @param element
     * @return
     */

    @Override
    public int getIndexOf(V element) {
        return indexOf(element);
        //return getIndex(observableValuesMap.get(element));
    }


    private class IteratorKlass implements Iterator<ObservableObject<V>> {

        private int curPos = 0;

        private List<ObservableObject<V>> list = new ArrayList<>(observableValuesMap.values());

        @Override
        public boolean hasNext() {
            return curPos < list.size();
        }

        @Override
        public ObservableObject<V> next() {
            //   List<Object> list = Arrays.asList(observableValuesMap.entrySet().toArray());
            return list.get(curPos++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }


//        @Override
//        public void forEachRemaining(Consumer<? super ObservableObject<V>> action) {
//            throw new NotImplementedException();
//        }
    }

    private class IterableKlass implements Iterable<ObservableObject<V>> {

        private Iterator<ObservableObject<V>> iterator = new IteratorKlass();

        @Override
        public Iterator<ObservableObject<V>> iterator() {
            return iterator;
        }


//        @Override
////        public void forEach(Consumer<? super ObservableObject<V>> action) {
////            while (iterator.hasNext())
////                action.accept(iterator.next());
////        }
////
////        @Override
////        public Spliterator<ObservableObject<V>> spliterator() {
////            throw new NotImplementedException();
////        }

    }

    //            eventListener.handleEvent(
//                    new BaseEvent<>(EventType.ADD_ELEMENT_EVENT, valueChanged), index);
    //        for(Map.Entry<String, EventListener<T>> entry : eventListeners.entrySet()) {
//            entry.getValue().handleEvent(t);
//        }
    private List<EventListener<V>> getEventList(EventType eventType) {
        switch (eventType) {
            case ADD_ELEMENT_EVENT:
                return onAddEventListeners;

            case REMOVE_ELEMENT_EVENT:
                return onRemoveEventListeners;

            case CHANGE_ELEMENT_EVENT:
                return onChangeEventListeners;

            case READ_ELEMENT_EVENT:
                return onGetEventListeners;

            default:
                return null;
        }
    }
}
