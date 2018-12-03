package com.javando.collections.api;

import java.lang.reflect.Method;


public class ObservableCollections {

 //   private static final Logger log = LoggerFactory.getLogger(ObservableCollections.class);


    public static <T> ObservableList<T> newArrayList() {
        // return InstanceResolver.newArrayList();
        ObservableList<T> obs = null;

        try {
            Class klass = Class.forName("com.javando.collections.InstanceResolver");
            return (ObservableList<T>) klass
                .getMethod("newObservableArrayList")
                .invoke(klass.newInstance());
//            Object instance = klass.newInstance();
//            Method m = klass.getMethod("newObservableArrayList");
//            Object o = m.invoke(instance);
//            obs = (ObservableList<T>) o;

        } catch (ClassNotFoundException e) {
            //log.error("Error instantiating InstanceResolver - Class not found (Have you added the api implementation ? If no, you can add the reference implementation: org.javando:observable-collections:1.0.RELEASE)");
            System.out.println("Error instantiating InstanceResolver - Class not found (Have you added the api implementation ? If no, you can add the reference implementation: org.javando:observable-collections:1.0.RELEASE)");
            e.printStackTrace();
        } catch(Exception e) {
            //log.error("Error instantiating InstanceResolver - Unknown error");
            System.out.println("System.out.println(Error instantiating InstanceResolver - Unknown error);");
            e.printStackTrace();
        }

        return obs;
    }

    public static <T extends Comparable<T>> ObservableList<T> newSortedArrayList() {
        //  return InstanceResolver.newSortedArrayList();
        return null;
    }

    public static <T> ObservableUnit<T> getObservableUnit(T objectToObserve) {
        ObservableUnit<T> unit = null;

        try {
            Class klass = Class.forName("com.javando.collections.InstanceResolver");
            Object instance = klass.newInstance();
            Method m = klass.getMethod("newObservableUnit");
            unit = (ObservableUnit<T>) m.invoke(instance);
            unit.setObservableObject(objectToObserve);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unit;
    }
}
