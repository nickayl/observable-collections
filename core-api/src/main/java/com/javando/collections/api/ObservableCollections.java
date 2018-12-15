package com.javando.collections.api;

public class ObservableCollections {

 //   private static final Logger log = LoggerFactory.getLogger(ObservableCollections.class);

    private static Class instanceResolverClass;
    private static Object instance;

    static {
        try {
            instanceResolverClass = Class.forName("com.javando.collections.InstanceResolver");
            instance = instanceResolverClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error instantiating InstanceResolver - Class not found (Have you added the api implementation ? If no, you can add the reference implementation: org.javando:observable-collections:1.0.RELEASE)");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static <T> ObservableList<T> newArrayList() {
        ObservableList<T> obs = null;

        try {
            return (ObservableList<T>) instanceResolverClass
                .getMethod("newObservableArrayList")
                .invoke(instance);

        } catch(Exception e) {
            System.out.println("System.out.println(Error instantiating InstanceResolver - Unknown error);");
            e.printStackTrace();
        }

        return obs;
    }

    public static <T extends Comparable<T>> ObservableList<T> newSortedArrayList() {
        ObservableList<T> sortedList = null;
        try {
            sortedList = (ObservableList<T>)
                    instanceResolverClass.getMethod("newSortedArrayList")
                    .invoke(instance);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sortedList;
    }

    public static <T> ObservableUnit<T> getObservableUnit(T objectToObserve) {
        ObservableUnit<T> unit = null;

        try {
            unit = (ObservableUnit<T>)  instanceResolverClass
                    .getMethod("newObservableUnit")
                    .invoke(instance);

            if(objectToObserve != null)
                unit.setValue(objectToObserve);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return unit;
    }

    public static <T> ObservableUnit<T> getObservableUnit() {
        return getObservableUnit(null);
    }
}
