package com.optimaize.hostrules4j.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Like a set with unique entries (equals, not identity) that are ordered by the value.
 *
 * <p>No synchronization is done internally.</p>
 *
 * <p>NOTE: THIS CLASS IS COPY-PASTED FROM THE crema-collections LIBRARY TO AVOID THE DEPENDENCY.</p>
 *
 * @author aa
 */
@SuppressWarnings({"JavaDoc"})
public class PrioritySet<E,K extends Comparable<K>> {

    @NotNull
    private final Map<E,K> items = new HashMap<>();

    private final boolean ascending;


    /**
     * Creates a set with ascending order.
     * @see #desc()
     */
    public static <E,K extends Comparable<K>> PrioritySet<E,K> asc() {
        return new PrioritySet<>(true);
    }
    /**
     * Creates a set with descending order.
     * @see #asc()
     */
    public static <E,K extends Comparable<K>> PrioritySet<E,K> desc() {
        return new PrioritySet<>(false);
    }


    /**
     */
    private PrioritySet(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * If such an element is there already then the 'better' one is kept.
     * Better means larger when comparable descending, and smaller when ascending.
     * @return <code>true</code> when the new one was added (either because it was not there yet, or because it was 'better'.
     */
    public boolean add(@NotNull E e, @NotNull K prio) {
        K oldPrio = items.get(e);
        if (oldPrio==null) {
            items.put(e, prio);
            return true;
        } else {
            if (isBetter(prio, oldPrio)) {
                items.put(e, prio);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isBetter(K prio, K oldPrio) {
        if (ascending) {
            return oldPrio.compareTo(prio) > 0;
        } else {
            return oldPrio.compareTo(prio) < 0;
        }
    }

    /**
     * @return A new Set with the entries correctly sorted. Disconnected from this object.
     */
    @NotNull
    public Set<E> toSet() {
        return toMap().keySet();
    }

    /**
     * @return A new List with the entries correctly sorted. Disconnected from this object.
     */
    @NotNull
    public List<E> toList() {
        return new ArrayList<>( toSet() );
    }

    /**
     * @return A new Map with the entries correctly sorted. Disconnected from this object.
     */
    @NotNull
    public Map<E,K> toMap() {
        return Sorting.sortByValue(items, getSorting());
    }

    @NotNull
    private Sorting.Order getSorting() {
        return (ascending) ? Sorting.Order.ASC : Sorting.Order.DESC;
    }


    @Override
    public String toString() {
        return "PrioritySet{" +
                "items=" + items.values() +
                '}';
    }

}
