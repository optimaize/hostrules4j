package com.optimaize.hostrules4j.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Static methods for collections sorting stuff.
 *
 * <p>NOTE: THIS CLASS IS COPY-PASTED FROM THE crema-collections LIBRARY TO AVOID THE DEPENDENCY.</p>
 *
 * @author aa
 */
public class Sorting {

    public enum Order {
        ASC,
        DESC
    }


    /**
     * Sorts in the given order.
     */
    @NotNull
    public static <K, V extends Comparable<? super V>> Map<K,V> sortByValue(@NotNull Map<K,V> map, @NotNull Order order) {
        if (order== Order.ASC) {
            //noinspection unchecked
            return sortByValue(map, COMPARATOR_ASC);
        } else {
            assert order == Order.DESC;
            //noinspection unchecked
            return sortByValue(map, COMPARATOR_DESC);
        }
    }
    /**
     * Sorts using the given comparator.
     */
    @NotNull
    public static <K, V extends Comparable<? super V>> Map<K,V> sortByValue(@NotNull Map<K,V> map, @NotNull Comparator<Map.Entry<?, V>> comparator) {
        Map<K,V> result = new LinkedHashMap<>();
        for (K k : getKeysSortedByValue(map, comparator)) {
            result.put(k, map.get(k));
        }
        return result;
    }



    private static final Comparator COMPARATOR_ASC  = new AscComparator();
    private static final Comparator COMPARATOR_DESC = new DescComparator();

    private static final class AscComparator<V extends Comparable<? super V>> implements Comparator<Map.Entry<?, V>> {
        public int compare(Map.Entry<?, V> o1, Map.Entry<?, V> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }
    private static final class DescComparator<V extends Comparable<? super V>> implements Comparator<Map.Entry<?, V>> {
        public int compare(Map.Entry<?, V> o1, Map.Entry<?, V> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }
    private static <K, V extends Comparable<? super V>> List<K> getKeysSortedByValue(@NotNull Map<K, V> map, @NotNull Comparator<Map.Entry<?, V>> comparator) {
        //code source: http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
        final int size = map.size();
        final List reusedList = new ArrayList(size);
        final List<Map.Entry<K, V>> meView = reusedList;
        meView.addAll(map.entrySet());
        Collections.sort(meView, comparator);
        final List<K> keyView = reusedList;
        for (int i = 0; i < size; i++) {
            keyView.set(i, meView.get(i).getKey());
        }
        return keyView;
    }



    /**
     * Sorts in descending order.
     */
    @NotNull
    @Deprecated
    public static <T> Map<T,Integer> sortByValue(@NotNull Map<T,Integer> map) {
        List<Map.Entry<T,Integer>> list = new LinkedList<Map.Entry<T,Integer>>(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        Map<T,Integer> result = new LinkedHashMap<T,Integer>();
        for (Map.Entry<T, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


}
