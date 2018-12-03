package com.javando.collections;

import java.util.Objects;


class ObservableValue<T> extends ObservableObject<T> {

  //  private EventListener<T> onChangeListener;
 // private static final Logger log = LoggerFactory.getLogger(ObservableValue.class);

    private T value;
    private int index = -1;

    private Observer<T> observer;

    protected ObservableValue(T value) {
        this.value = value;
    }

    protected ObservableValue(T value, int index) {
        this.value = value;
        this.index = index;
    }

    @Override
    protected void registerObserver(Observer<T> observer) {
        this.observer = observer;
    }

    @Override
    protected void unregisterObserver(Observer<T> vs) {
        this.observer = null;
    }

    @Override
    public void setValue(T newValue) {
        if(observer == null) {
            //log.warn("Inside ObservableValue: this.observer null! returning without do nothing");
            System.out.println("Inside ObservableValue: this.observer null! returning without do nothing");
            return ;
        }
       // ObservableObject<T> oldValue = new ObservableValue<>(value, index);
        T oldValue = this.value;
        this.value = newValue;
        observer.notifyChange(this, oldValue);
//        onChangeListener.handleEvent(new ChangeEvent<>(this), index);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservableValue that = (ObservableValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    //    void setOnValueChangeEventListener(EventListener<T> onChangeListener) {
//        this.onChangeListener = onChangeListener;
//    }

}
